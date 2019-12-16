package designpatterns;

public interface Pipeline {

    Pipeline fireTaskReceived();

    Pipeline fireTaskFiltered();

    Pipeline fireTaskExecuted();

    Pipeline fireAfterCompletion();
}
