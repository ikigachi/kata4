package software.ulpgc.katas.infrastructure.adapters;

import io.javalin.http.Context;
import software.ulpgc.katas.architecture.ports.Input;

public class ContextInput implements Input {
    private final Context context;

    public ContextInput(Context context) {
        this.context = context;
    }

    @Override
    public Parameter get(String name) {
        return new Parameter() {
            @Override
            public String asString() {
                return context.queryParam("n");
            }

            @Override
            public int asInteger() {
                return Integer.parseInt(asString());
            }
        };
    }
}
