package xyz.luan.console.parser;

import java.util.Map;

public class MultipleCalls implements Call {

    private static final long serialVersionUID = 5989173280784813959L;

    private Call[] calls;
    private Operator operator;

    public MultipleCalls(Call[] calls, Operator operator) {
        this.calls = calls;
        this.operator = operator;
    }

    public enum Operator {
        ALL {
            @Override
            public boolean continueOn(CallResult c) {
                return true;
            }
        }, AND {
            @Override
            public boolean continueOn(CallResult c) {
                return c == CallResult.SUCCESS;
            }
        }, OR {
            @Override
            public boolean continueOn(CallResult c) {
                return c != CallResult.SUCCESS;
            }
        };
        
        public abstract boolean continueOn(CallResult c);
    }

    @Override
    public CallResult invoke(Map<String, ControllerRef<?>> controllers) {
        CallResult result = CallResult.SUCCESS;
        for (Call c : calls) {
            result = c.invoke(controllers);
            if (result == CallResult.QUIT) {
                return CallResult.QUIT;
            }
            if (!operator.continueOn(result)) {
                return result;
            }
        }
        return result;
    }
}
