package org.java_websocket.client;

import com.pekall.push.test.*;
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


	// The URI this channel is supposed to connect to.
	private String queryServerAddr = null;

    private URI pushServerUris[] = null;

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
	public WebSocketClientTest(String serverURI, int sockCount) {
        this(serverURI, new Draft_17(), sockCount);
    }

	/**
	 * Constructs a WebSocketClient instance and sets it to the connect to the
	 * specified URI. The channel does not attampt to connect automatically. The connection
	 * will be established once you call <var>connect</var>.
	 */
	public WebSocketClientTest(String serverUri, Draft draft, int sockCount) {
        this(serverUri, draft, null, 5000, sockCount);
    }

	public WebSocketClientTest(String serverAddr, Draft protocolDraft,
                               Map<String, String> httpHeaders, int connectTimeout, int sockCount) {
		if( serverAddr == null ) {
			throw new IllegalArgumentException();
		} else if( protocolDraft == null ) {
			throw new IllegalArgumentException( "null as draft is permitted for `WebSocketServer` only!" );
		}
		this.queryServerAddr = serverAddr;
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

    public String getStatisticString() {
        int nullSock = 0;
        int notOpen = 0;
        int connecting = 0;
        int open = 0;
        int closing = 0;
        int closed = 0;

        for (int i = 0; i < sockCount; i++) {
            if(engines[i] == null) {
                nullSock++;
                continue;
            }

            if (engines[i].getReadyState() == READYSTATE.NOT_YET_CONNECTED) {
                notOpen++;
            } else if (engines[i].getReadyState() == READYSTATE.CONNECTING) {
                connecting++;
            } else if (engines[i].getReadyState() == READYSTATE.OPEN) {
                open++;
            } else if (engines[i].getReadyState() == READYSTATE.CLOSING) {
                closing++;
            } else if (engines[i].getReadyState() == READYSTATE.CLOSED) {
                closed++;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("websocket state: [")
                .append("null: ").append(nullSock)
                .append(", not yet open: ").append(notOpen)
                .append(", connecting: ").append(connecting)
                .append(", open: ").append(open)
                .append(", closing: ").append(closing)
                .append(", closed: ").append(closed)
                .append("]");
        return sb.toString();
    }

	/**
	 * Initiates the websocket connection. This method does not block.
	 */
	public synchronized void connect() {
        Debug.log("create receive thread");
        handshakeDone = false;

        recvThread = new Thread(this);
        recvThread.setName("recv thread");
        recvThread.start();

        Debug.log("wait for handshake ...");
        synchronized (initLock) {
            while (!handshakeDone) {
                try {
                    initLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Debug.log("handshake done!");
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

    public void register(int id) {
        Debug.log("register, id: " + PushConstant.DEVICE_ID_PREFIX +
                PushConstant.DEVICE_BEGIN_ID + id);
        send(PushMessageManager.genShakeHandMessage(
                PushConstant.DEVICE_BEGIN_ID + id).toJson(), id);
    }

    void send(String text, int id) throws NotYetConnectedException {
        if (engines[id] == null || !engines[id].isOpen()) return;
        engines[id].send(text);
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
        Debug.log("receive thread created!");
        initSockets();
        doHandshakes();
        createSendThread();

        receiveThreadLoop();

        // todo: check close
        // assert ( sockets.isClosed() );
    }

    private long mTotalReadCnt = 0;

    private void receiveThreadLoop() {
        byte[] rawBuffer = new byte[WebSocketImpl.RCVBUF];
        while (!isClosed()) {
            int readCnt = receiveParseData(rawBuffer);
            mTotalReadCnt += readCnt;

            synchronized (initLock) {
                // We should resume main thread after about 95% handshake is done
                if (!handshakeDone && mTotalReadCnt > sockCount * 0.99) {
                    handshakeDone = true;
                    initLock.notify();

                    Debug.log("handshake total recv cnt: " + mTotalReadCnt);
                }
            }

            // accelerate receiving speed
            if(readCnt > 0) continue;

            // todo: make schedule plan
            try {
                Thread.sleep(2 * 1000L);
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
            Debug.log("read count: " + readCnt);

        return readCnt;
    }

    private void closeConnections(int code, String msg) {
        for (int i = 0; i < sockCount; i++) {
            if(engines[i] == null) continue;
            engines[i].closeConnection(code, msg);
        }
    }

    private void createSendThread() {
        Debug.log("create send thread");
        sendThread = new Thread(new WebsocketWriteThread());
        sendThread.setName("send thread");
        sendThread.start();
    }

    private void initSockets() {
        Debug.log("init sockets begin");
        pushServerUris = new URI[sockCount];

        for (int i = 0; i < sockCount; i++) {
            assert (engines[i] != null);

            String queryUrl = queryServerAddr + PushConstant.PUSH_QUERY_PATH +
                    PushConstant.PUSH_QUERY_PARAM + PushConstant.DEVICE_BEGIN_ID + i;
            Debug.log("query push server addr:" + queryUrl);

            ServerAddress serverAddress;
            try {
                serverAddress = ServerAddrQuery.query(queryUrl);
                Debug.logVerbose("serverAddress: " + serverAddress.toString());
                pushServerUris[i] = URI.create(PushConstant.WS_SCHEME + serverAddress.getIp()
                        + ":" + serverAddress.getPort() + PushConstant.WS_PATH);
                Debug.log("query push server uri:" + pushServerUris[i].toString());
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            try {
                Debug.log("init socket: " + i +
                        ", host: " + pushServerUris[i].getHost() +
                        ", port: " + pushServerUris[i].getPort());
                sockets[i] = new Socket(proxy);

                sockets[i].connect(new InetSocketAddress(pushServerUris[i].getHost(),
                        pushServerUris[i].getPort()), connectTimeout);
                sockInStreams[i] = sockets[i].getInputStream();
                sockOutStreams[i] = sockets[i].getOutputStream();
            } catch (Exception e) {
                onWebsocketError(engines[i], e);
                engines[i].closeConnection(CloseFrame.NEVER_CONNECTED, e.getMessage());
                sockets[i] = null;
            }
        }
        Debug.log("init sockets done!");
    }

    private int mTotalSendHandshake = 0;
    private void doHandshakes() {
        Debug.log("handshake begin");
        for (int i = 0; i < sockCount; i++) {
            // ignore close socket
            if(engines[i] == null) continue;

            try {
                sendHandshake(i);
            } catch (InvalidHandshakeException e) {
                onWebsocketError(engines[i], e);
                engines[i].closeConnection(CloseFrame.NEVER_CONNECTED, e.getMessage());
            }
            mTotalSendHandshake++;
        }
        Debug.log("handshake in queue [" + mTotalSendHandshake + "]");
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

    private void sendHandshake(int i) throws InvalidHandshakeException {
        assert (engines[i] != null);

        String path;
        String part1 = pushServerUris[i].getPath();
        String part2 = pushServerUris[i].getQuery();
        if (part1 == null || part1.length() == 0)
            path = "/";
        else
            path = part1;
        if (part2 != null)
            path += "?" + part2;
        String host = pushServerUris[i].getHost() + ":" + pushServerUris[i].getPort();

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
    public final void onWebsocketMessage(WebSocket conn, String message) {
        PushMessage pushMessage = PushMessage.fromJson(message);
        if (pushMessage == null) {
            Debug.log("push message is null");
            return;
        }

        Debug.logVerbose("onWebsocketMessage: " + message);

        if(pushMessage.getType() == PushMessage.MsgType.TYPE_SEND) {
            PushMessage respMsg = PushMessageManager.createResponseMessage(pushMessage.getId());
            Debug.logVerbose("push message:" + pushMessage.toString());
            Debug.logVerbose("send resp msg:" + respMsg.toString());
            conn.send(respMsg.toJson());

            onMessage(message);
        }
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

        if(PushConstant.DEVICE_BEGIN_ID == -1)
            return;

        // todo: performance optimize
        int i = 0;
        for (; i < sockCount; i++) {
            if (conn == engines[i]) break;
        }
        if(i >= sockCount) {
            Debug.logError("can not find socket");
            return;
        }

        register(i);
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
            Debug.log("send thread created");
            // Thread.currentThread().setName("WebsocketWriteThread");

            while (!Thread.interrupted()) {
                // accelerate sending speed
                if (sendData() > 0) continue;

                // todo: make schedule plan
                try {
                    Thread.sleep(2 * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private int sendData() {
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

            if(sendCnt > 0) Debug.log("send count: " + sendCnt);
            return sendCnt;
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
		// return queryServerAddr.getPath();
        return PushConstant.WS_PATH;
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
