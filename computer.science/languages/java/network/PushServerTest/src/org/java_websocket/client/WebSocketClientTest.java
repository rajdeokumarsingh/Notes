package org.java_websocket.client;

import com.pekall.push.test.Statistics;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketAdapter;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.Framedata.Opcode;
import org.java_websocket.handshake.HandshakeImpl1Client;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * A subclass must implement at least <var>onOpen</var>, <var>onClose</var>,
 * and <var>onMessage</var> to be useful. At runtime the user is expected to
 * establish a connection via {@link #connect()}, then receive events like
 * {@link #onMessage(String)} via the overloaded methods and to
 * {@link #send(String)} data to the server.
 */
public abstract class WebSocketClientTest
        extends WebSocketAdapter implements Runnable, WebSocket {

    // todo: sync method


    // todo: refactory
    class WebSockCore {
        private WebSocketImpl sockImpl;
        private Socket socket;
        private InputStream sockInStream;
        private OutputStream sockOutStream;
    }

    private int sockCount = 1;

    /**
	 * The URI this channel is supposed to connect to.
	 */
	protected URI uri = null;

	private WebSocketImpl[] engines;
	private Socket[] sockets;
	private InputStream[] sockInStreams;
	private OutputStream[] sockOutStreams;

	private Proxy proxy = Proxy.NO_PROXY;

	private Thread sendThread;
    private Thread recvThread;
    private Object initLock = new Object();
    private boolean handshakeDone;

	private Draft draft;

	private Map<String,String> headers;

	private CountDownLatch connectLatch = new CountDownLatch( 1 );

	private CountDownLatch closeLatch = new CountDownLatch( 1 );

	private int connectTimeout = 0;

	/** This open a websocket connection as specified by rfc6455 */
	public WebSocketClientTest(URI serverURI, int sockCount) {
        this(serverURI, new Draft_17(), sockCount);
    }

	/**
	 * Constructs a WebSocketClient instance and sets it to the connect to the
	 * specified URI. The channel does not attampt to connect automatically. The connection
	 * will be established once you call <var>connect</var>.
	 */
	public WebSocketClientTest(URI serverUri, Draft draft, int sockCount) {
        this(serverUri, draft, null, 1000, sockCount);
    }

	public WebSocketClientTest(URI serverUri, Draft protocolDraft,
                               Map<String, String> httpHeaders, int connectTimeout, int sockCount) {
		if( serverUri == null ) {
			throw new IllegalArgumentException();
		} else if( protocolDraft == null ) {
			throw new IllegalArgumentException( "null as draft is permitted for `WebSocketServer` only!" );
		}
		this.uri = serverUri;
		this.draft = protocolDraft;
		this.headers = httpHeaders;
		this.connectTimeout = connectTimeout;

        initSockContainer(sockCount);
	}

    /**
	 * Returns the protocol version this channel uses.<br>
	 * For more infos see https://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
	 */
	public Draft getDraft() {
		return draft;
	}

	/**
	 * Initiates the websocket connection. This method does not block.
	 */
	public synchronized void connect() {
        System.out.println("create receive thread");
        handshakeDone = false;

        recvThread = new Thread(this);
        recvThread.start();

        System.out.println("wait for handshake ...");
        synchronized (initLock) {
            while (!handshakeDone) {
                try {
                    initLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("handshake done!");
    }

    /**
	 * Initiates the websocket close handshake. This method does not block<br>
	 * In oder to make sure the connection is closed use <code>closeBlocking</code>
	 */
	public void close() {
		if( sendThread != null ) {
            // todo: check clear a connection or all connection?
            // todo: refactory
            // closeConnections(CloseFrame.NORMAL, "user close");
            for (int i = 0; i < sockCount; i++) {
                if (engines[i] == null) continue;

                engines[i].close(CloseFrame.NORMAL);
            }
        }
	}

    /**
	 * Sends <var>text</var> to the connected websocket server.
	 * 
	 * @param text
	 *            The string which will be transmitted.
	 */
	public void send( String text ) throws NotYetConnectedException {
        for (int i = 0; i < sockCount; i++) {
            if (engines[i] == null) continue;
            engines[i].send(text);
        }
	}

	/**
	 * Sends binary <var> data</var> to the connected webSocket server.
	 * 
	 * @param data
	 *            The byte-Array of data to send to the WebSocket server.
	 */
	public void send( byte[] data ) throws NotYetConnectedException {
        for (int i = 0; i < sockCount; i++) {
            if (engines[i] == null) continue;
            engines[i].send(data);
        }
	}

    /**
     * Ping <var>text</var> to the connected WebSocket server.
     *
     * @param text
     *            The String to ping to the WebSocket server.
     */
    public void ping( String text ) throws NotYetConnectedException {
        for (int i = 0; i < sockCount; i++) {
            if (engines[i] == null || !engines[i].isOpen()) continue;
            engines[i].ping(text);

            Statistics.getInstance().ping();
        }
    }

	public void run() {
        System.out.println("receive thread created!");
        initSockets();
        doHandshakes();
        createSendThread();

        receiveThreadLoop();

        // todo: check close
        // assert ( sockets.isClosed() );
    }

    private void receiveThreadLoop() {
        byte[] rawBuffer = new byte[WebSocketImpl.RCVBUF];
        while (!isClosed()) {
            int readCnt = receiveParseData(rawBuffer);

            synchronized (initLock) {
                if(readCnt > 0 && !handshakeDone) {
                    handshakeDone = true;
                    initLock.notify();
                }
            }

            // todo: make schedule plan
            try {
                Thread.sleep(5 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        enginesEot();
    }

    private int receiveParseData(byte[] rawBuffer) {
        int readCnt = 0;
        for (int i = 0; i < sockCount; i++) {
            // ignore close connection
            if(engines[i] == null) continue;

            try {
                // Get available data count from network, not blocking
                int available = sockInStreams[i].available();
                if(available <= 0) continue;

                int readBytes;
                if ((readBytes = sockInStreams[i].read(rawBuffer)) != -1) {
                    engines[i].decode(ByteBuffer.wrap(rawBuffer, 0, readBytes));
                }
                readCnt++;
                // todo: sleep sometime?
            } catch ( IOException e ) {
                engines[i].eot();
            } catch ( RuntimeException e ) {
                // this catch case covers internal errors only and
                // indicates a bug in this websocket implementation
                onError( e );
                closeConnections(CloseFrame.ABNORMAL_CLOSE, e.getMessage());
            }
        }
        if(readCnt > 0)
            System.out.println("read count: " + readCnt);

        return readCnt;
    }

    private void closeConnections(int code, String msg) {
        for (int i = 0; i < sockCount; i++) {
            if(engines[i] == null) continue;
            engines[i].closeConnection(code, msg);
        }
    }

    private void createSendThread() {
        System.out.println("create send thread");
        sendThread = new Thread(new WebsocketWriteThread());
        sendThread.start();
    }

    private void initSockets() {
        System.out.println("init sockets begin");
        for (int i = 0; i < sockCount; i++) {
            assert (engines[i] != null);

            try {
                System.out.println("init socket: " + i);
                /*
                if (sockets[i] == null) {
                    sockets[i] = new Socket(proxy);
                } else if (sockets[i].isClosed()) {
                    throw new IOException();
                }
                if (!sockets[i].isBound()) {
                    sockets[i].connect(new InetSocketAddress(uri.getHost(), getPort()), connectTimeout);
                }
                */
                sockets[i] = new Socket(proxy);
                sockets[i].connect(new InetSocketAddress(uri.getHost(), getPort()), connectTimeout);
                sockInStreams[i] = sockets[i].getInputStream();
                sockOutStreams[i] = sockets[i].getOutputStream();
            } catch (Exception e) {
                onWebsocketError(engines[i], e);
                engines[i].closeConnection(CloseFrame.NEVER_CONNECTED, e.getMessage());
                sockets[i] = null;
            }
        }
        System.out.println("init sockets done!");
    }

    private void doHandshakes() {
        System.out.println("handshake begin");
        for (int i = 0; i < sockCount; i++) {
            // ignore close socket
            if(engines[i] == null) continue;

            try {
                sendHandshake(i);
            } catch (InvalidHandshakeException e) {
                onWebsocketError(engines[i], e);
                engines[i].closeConnection(CloseFrame.NEVER_CONNECTED, e.getMessage());
            }
        }
        System.out.println("handshake in queue");
    }

    private void clearSockResource(int i) {
        if(sockets[i] != null) {
            try {
                sockets[i].close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sockets[i] = null;
        sockInStreams[i] = null;
        sockOutStreams[i] = null;
        engines[i] = null;
    }

    private void enginesEot() {
        for (int i = 0; i < sockCount; i++) {
            if(engines[i] == null) continue;
            engines[i].eot();
        }
    }

    private int getPort() {
		int port = uri.getPort();
		if( port == -1 ) {
			String scheme = uri.getScheme();
			if( scheme.equals( "wss" ) ) {
				return WebSocket.DEFAULT_WSS_PORT;
			} else if( scheme.equals( "ws" ) ) {
				return WebSocket.DEFAULT_PORT;
			} else {
				throw new RuntimeException( "unkonow scheme" + scheme );
			}
		}
		return port;
	}

	private void sendHandshake(int i) throws InvalidHandshakeException {
        assert (engines[i] != null);

        String path;
        String part1 = uri.getPath();
        String part2 = uri.getQuery();
        if (part1 == null || part1.length() == 0)
            path = "/";
        else
            path = part1;
        if (part2 != null)
            path += "?" + part2;
        int port = getPort();
        String host = uri.getHost() + (port != WebSocket.DEFAULT_PORT ? ":" + port : "");

        HandshakeImpl1Client handshake = new HandshakeImpl1Client();
        handshake.setResourceDescriptor(path);
        handshake.put("Host", host);
        if (headers != null) {
            for (Map.Entry<String, String> kv : headers.entrySet()) {
                handshake.put(kv.getKey(), kv.getValue());
            }
        }
        engines[i].startHandshake(handshake);
    }

	/**
	 * This represents the state of the connection.
	 */
	public READYSTATE getReadyState() {
        // todo: check relative logic
        for (int i = 0; i < sockCount; i++) {
            if(engines[i] == null) continue;

            return engines[i].getReadyState();
        }
        return  READYSTATE.CLOSED;
	}

	/**
	 * Calls subclass' implementation of <var>onMessage</var>.
	 */
	@Override
	public final void onWebsocketMessage( WebSocket conn, String message ) {
		onMessage( message );
	}

	@Override
	public final void onWebsocketMessage( WebSocket conn, ByteBuffer blob ) {
		onMessage( blob );
	}

	@Override
	public void onWebsocketMessageFragment( WebSocket conn, Framedata frame ) {
		onFragment( frame );
	}

	/**
	 * Calls subclass' implementation of <var>onOpen</var>.
	 */
	@Override
	public final void onWebsocketOpen( WebSocket conn, Handshakedata handshake ) {
        connectLatch.countDown();
        onOpen((ServerHandshake) handshake);
    }

	/**
	 * Calls subclass' implementation of <var>onClose</var>.
	 */
	@Override
	public final void onWebsocketClose( WebSocket conn, int code, String reason, boolean remote ) {
		connectLatch.countDown();
		closeLatch.countDown();

        // todo: exit the thread
		// if( sendThread != null ) sendThread.interrupt();

        // todo: performance optimize
        for (int i = 0; i < sockCount; i++) {
            if (conn == engines[i]) {
                clearSockResource(i);
                break;
            }
        }
        onClose(code, reason, remote);
    }

	/**
	 * Calls subclass' implementation of <var>onIOError</var>.
	 */
	@Override
	public final void onWebsocketError( WebSocket conn, Exception ex ) {
		onError( ex );
	}

	@Override
	public final void onWriteDemand( WebSocket conn ) {
		// nothing to do
	}

	@Override
	public void onWebsocketCloseInitiated( WebSocket conn, int code, String reason ) {
		onCloseInitiated( code, reason );
	}

	@Override
	public void onWebsocketClosing( WebSocket conn, int code, String reason, boolean remote ) {
		onClosing( code, reason, remote );
	}

	public void onCloseInitiated( int code, String reason ) {
	}

	public void onClosing( int code, String reason, boolean remote ) {
	}

	public WebSocket[] getConnections() {
		return engines;
	}

	@Override
	public InetSocketAddress getLocalSocketAddress( WebSocket conn ) {
		// if( sockets != null ) return (InetSocketAddress) sockets.getLocalSocketAddress();
        for (int i = 0; i < sockCount; i++) {
            if (sockets[i] == null) continue;
            return (InetSocketAddress) sockets[i].getLocalSocketAddress();
        }

		return null;
	}

	@Override
	public InetSocketAddress getRemoteSocketAddress( WebSocket conn ) {
		// if( sockets != null ) return (InetSocketAddress) sockets.getLocalSocketAddress();
        for (int i = 0; i < sockCount; i++) {
            if (sockets[i] == null) continue;
            return (InetSocketAddress) sockets[i].getLocalSocketAddress();
        }
        return null;
	}

	// ABTRACT METHODS /////////////////////////////////////////////////////////
	public abstract void onOpen( ServerHandshake handshakedata );
	public abstract void onMessage( String message );
	public abstract void onClose( int code, String reason, boolean remote );
	public abstract void onError( Exception ex );
	public void onMessage( ByteBuffer bytes ) {
	}
	public void onFragment( Framedata frame ) {
	}

	private class WebsocketWriteThread implements Runnable {
		@Override
		public void run() {
            System.out.println("send thread created");
            Thread.currentThread().setName("WebsocketWriteThread");

            while (!Thread.interrupted()) {
                sendData();

                // todo: make schedule plan
                try {
                    Thread.sleep(5 * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void sendData() {
            int sendCnt = 0;
            for (int i = 0; i < sockCount; i++) {
                if (engines[i] == null) continue;
                try {
                    if (!engines[i].hasBufferedData()) continue;

                    ByteBuffer buffer = engines[i].outQueue.take();
                    sockOutStreams[i].write(buffer.array(), 0, buffer.limit());
                    sockOutStreams[i].flush();

                    sendCnt++;
                } catch (IOException e) {
                    engines[i].eot();
                } catch (InterruptedException e) {
                    // this thread is regularly terminated via an interrupt
                }
            }

            if(sendCnt > 0)
                System.out.println("send count: " + sendCnt);
        }
    }

	public void setProxy( Proxy proxy ) {
		if( proxy == null )
			throw new IllegalArgumentException();
		this.proxy = proxy;
	}

	/**
	 * Accepts bound and unbound sockets.<br>
	 * This method must be called before <code>connect</code>.
	 * If the given sockets is not yet bound it will be bound to the uri specified in the constructor.
	 **/
	public void setSocket( Socket socket ) {
        throw new IllegalStateException( "sockets has already been set" );
        /*
		if( this.sockets != null ) {
			throw new IllegalStateException( "sockets has already been set" );
		}
		this.sockets = socket;
		*/
	}

	@Override
	public void sendFragmentedFrame( Opcode op, ByteBuffer buffer, boolean fin ) {
        for (int i = 0; i < sockCount; i++) {
            if(engines[i] == null) continue;
            engines[i].sendFragmentedFrame(op, buffer, fin);
        }
	}

	@Override
	public boolean isOpen() {
		// return engines.isOpen();
        for (int i = 0; i < sockCount; i++) {
            if(engines[i] == null) continue;
            if(engines[i].isOpen()) return true;
        }
        return false;
    }

	@Override
	public boolean isFlushAndClose() {
		// return engines.isFlushAndClose();
        for (int i = 0; i < sockCount; i++) {
            if(engines[i] == null) continue;
            if(!engines[i].isFlushAndClose())
                return false;
        }
        return true;
    }

	@Override
	public boolean isClosed() {
        for (int i = 0; i < sockCount; i++) {
            if(engines[i] == null) continue;
            if(!engines[i].isClosed())
                return false;
        }
        return true;
	}

	@Override
	public boolean isClosing() {
		// return engines.isClosing();
        for (int i = 0; i < sockCount; i++) {
            if(engines[i] == null) continue;

            if(!engines[i].isClosing())
                return false;
        }
        return true;
    }

	@Override
	public boolean isConnecting() {
		// return engines.isConnecting();
        for (int i = 0; i < sockCount; i++) {
            if(engines[i] == null) continue;

            if(!engines[i].isConnecting())
                return false;
        }
        return true;
	}

	@Override
	public boolean hasBufferedData() {
        // return engines.hasBufferedData();
        for (int i = 0; i < sockCount; i++) {
            if(engines[i] == null) continue;
            if (engines[i].hasBufferedData()) return true;
        }
        return false;
	}

    @Override
    public void close(int code) {
        // engines.close();
        for (int i = 0; i < sockCount; i++) {
            if (engines[i] == null) continue;
            engines[i].close();
        }
    }

	@Override
	public void close( int code, String message ) {
        // engines.close(code, message);
        for (int i = 0; i < sockCount; i++) {
            if (engines[i] == null) continue;
            engines[i].close(code, message);
        }
	}

	@Override
	public void closeConnection( int code, String message ) {
        // engines.closeConnection(code, message);
        for (int i = 0; i < sockCount; i++) {
            if (engines[i] == null) continue;
            engines[i].closeConnection(code, message);
        }
	}

	@Override
	public void send( ByteBuffer bytes ) throws IllegalArgumentException , NotYetConnectedException {
		// engines.send(bytes);
        for (int i = 0; i < sockCount; i++) {
            if (engines[i] == null) continue;
            engines[i].send(bytes);
        }
    }

	@Override
	public void sendFrame( Framedata framedata ) {
		// engines.sendFrame(framedata);
        for (int i = 0; i < sockCount; i++) {
            if (engines[i] == null) continue;
            engines[i].sendFrame(framedata);
        }
    }

	@Override
	public InetSocketAddress getLocalSocketAddress() {
        // return engines.getLocalSocketAddress();
        for (int i = 0; i < sockCount; i++) {
            if (engines[i] == null) continue;
            return engines[i].getLocalSocketAddress();
        }
        return null;
	}

	@Override
	public InetSocketAddress getRemoteSocketAddress() {
        // return engines.getRemoteSocketAddress();
        for (int i = 0; i < sockCount; i++) {
            if (engines[i] == null) continue;
            return engines[i].getRemoteSocketAddress();
        }
        return null;
	}
	
	@Override
	public String getResourceDescriptor() {
		return uri.getPath();
	}

    public void initSockContainer(int sockCount) {
        assert (sockCount > 0);

        this.sockCount = sockCount;
        engines = new WebSocketImpl[sockCount];
        sockets = new Socket[sockCount];
        sockInStreams = new InputStream[sockCount];
        sockOutStreams = new OutputStream[sockCount];

        for (int i = 0; i < sockCount; i++) {
            engines[i] = new WebSocketImpl(this, this.draft);
        }
    }
}
