package software.ulpgc.katas.architecture.ports;

public interface Input {
    Parameter get(String n);

    interface Parameter {
        String asString();
        int asInteger();
    }
}
