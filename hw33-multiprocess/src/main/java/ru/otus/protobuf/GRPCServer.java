package ru.otus.protobuf;

import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import ru.otus.protobuf.service.RemoteDBServiceImpl;

import java.io.IOException;

@Slf4j
@SuppressWarnings({"squid:S106"})
public class GRPCServer {

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {
        var remoteService = new RemoteDBServiceImpl();

        var server =
                ServerBuilder.forPort(SERVER_PORT).addService(remoteService).build();
        server.start();

        log.info("Server waiting for client connections...");
        server.awaitTermination();
    }
}