package myproject.model;

/**
 * Interface for active model objects.eg: car and light
 */
public interface Agent {
    void setup(double time);

    void commit(double time);
}

