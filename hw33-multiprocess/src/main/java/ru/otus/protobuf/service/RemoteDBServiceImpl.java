package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.RangeMessage;
import ru.otus.protobuf.RemoteServiceGrpc;
import ru.otus.protobuf.ValueMessage;

public class RemoteDBServiceImpl extends RemoteServiceGrpc.RemoteServiceImplBase {

    @Override
    public void generate(RangeMessage request, StreamObserver<ValueMessage> responseObserver) {
        long value = request.getFirstValue() - 1;

        while (value <= request.getLastValue()) {
            value++;

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            responseObserver.onNext(ValueMessage.newBuilder().setValue(value).build());
        }

        responseObserver.onCompleted();
    }
}
