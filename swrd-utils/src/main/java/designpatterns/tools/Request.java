package designpatterns.tools;

public class Request {
    private ContextCollector contextCollector;

    public ContextCollector getContextCollector() {
        return contextCollector;
    }

    public void setContextCollector(ContextCollector contextCollector) {
        this.contextCollector = contextCollector;
    }
}
