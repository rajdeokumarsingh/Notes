package com.pekall.push.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

import java.net.URI;

public class NWebSocketClient {

    private final URI uri;

    public NWebSocketClient(URI uri) {
        this.uri = uri;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            String protocol = uri.getScheme();
            if (!"ws".equals(protocol)) {
                throw new IllegalArgumentException("Unsupported protocol: " + protocol);
            }

            HttpHeaders customHeaders = new DefaultHttpHeaders();
            customHeaders.add("MyHeader", "MyValue");

            // Connect with V13 (RFC 6455 aka HyBi-17). You can change it to V08 or V00.
            // If you change it to V00, ping is not supported and remember to change
            // HttpResponseDecoder to WebSocketHttpResponseDecoder in the pipeline.
            final WebSocketClientHandler handler =
                    new WebSocketClientHandler(
                            WebSocketClientHandshakerFactory.newHandshaker(
                                    uri, WebSocketVersion.V13, null, false, customHeaders));

            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("http-codec", new HttpClientCodec());
                            pipeline.addLast("aggregator", new HttpObjectAggregator(8192));
                            pipeline.addLast("ws-handler", handler);
                        }
                    });

            System.out.println("WebSocket Client connecting");
            Channel ch = b.connect(uri.getHost(), uri.getPort()).sync().channel();
            handler.handshakeFuture().sync();

            // Send 10 messages and wait for responses
            System.out.println("WebSocket Client sending message");
            for (int i = 0; i < 10; i++) {
                ch.writeAndFlush(new TextWebSocketFrame("Message #" + i));
            }

            // Ping
            System.out.println("WebSocket Client sending ping");
            ch.writeAndFlush(new PingWebSocketFrame(Unpooled.copiedBuffer(new byte[]{1, 2, 3, 4, 5, 6})));

            // Close
            System.out.println("WebSocket Client sending close");
            ch.writeAndFlush(new CloseWebSocketFrame());

            // WebSocketClientHandler will close the connection when the server
            // responds to the CloseWebSocketFrame.
            ch.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
//        int count = Integer.valueOf(args[0]);
        int count = 10000;
        URI uri;
        if (args.length > 0) {
            uri = new URI(args[0]);
        } else {
            uri = new URI("ws://192.168.10.234:9090/websocket");
        }
        Channel[] chs = new Channel[count];
        for (int i = 0; i < count; i++) {
            Channel ch = new NWebSocketClient(uri).connect();
            Statistics.getInstance().create();
            chs[i] = ch;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Statistics.getInstance().getStatistics());
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        // Send 10 messages and wait for responses
//        System.out.println("WebSocket Client sending message");
//        for (int i = 0; i < 10; i++) {
//            ch.writeAndFlush(new TextWebSocketFrame("Message #" + i));
//        }

        while (true) {
            for (Channel ch : chs) {
                System.out.println("WebSocket Client sending ping");
                ch.writeAndFlush(new PingWebSocketFrame(Unpooled.copiedBuffer(new byte[]{1, 2, 3, 4, 5, 6})));
            }
            Thread.sleep(60 * 1000L);
        }

        // Ping
//        System.out.println("WebSocket Client sending ping");
//        ch.writeAndFlush(new PingWebSocketFrame(Unpooled.copiedBuffer(new byte[]{1, 2, 3, 4, 5, 6})));

        // Close
//        System.out.println("WebSocket Client sending close");
//        ch.writeAndFlush(new CloseWebSocketFrame());

        // WebSocketClientHandler will close the connection when the server
        // responds to the CloseWebSocketFrame.
//        ch.closeFuture().sync();
    }

    public Channel connect() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap b = new Bootstrap();
            String protocol = uri.getScheme();
            if (!"ws".equals(protocol)) {
                throw new IllegalArgumentException("Unsupported protocol: " + protocol);
            }

            HttpHeaders customHeaders = new DefaultHttpHeaders();
            customHeaders.add("MyHeader", "MyValue");

            // Connect with V13 (RFC 6455 aka HyBi-17). You can change it to V08 or V00.
            // If you change it to V00, ping is not supported and remember to change
            // HttpResponseDecoder to WebSocketHttpResponseDecoder in the pipeline.
            final WebSocketClientHandler handler =
                    new WebSocketClientHandler(
                            WebSocketClientHandshakerFactory.newHandshaker(
                                    uri, WebSocketVersion.V13, null, false, customHeaders));

            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("http-codec", new HttpClientCodec());
                            pipeline.addLast("aggregator", new HttpObjectAggregator(8192));
                            pipeline.addLast("ws-handler", handler);
                        }
                    });

            System.out.println("WebSocket Client connecting");
            Channel ch = b.connect(uri.getHost(), uri.getPort()).sync().channel();
            handler.handshakeFuture().sync();

            return ch;
    }
}