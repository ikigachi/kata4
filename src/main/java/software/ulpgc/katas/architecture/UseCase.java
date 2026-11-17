package software.ulpgc.katas.architecture;

import software.ulpgc.katas.architecture.ports.Input;
import software.ulpgc.katas.architecture.ports.Output;

public interface UseCase {
    Output execute(Input input);
}
