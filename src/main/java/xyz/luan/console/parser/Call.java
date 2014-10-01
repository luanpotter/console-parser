package xyz.luan.console.parser;

import java.io.Serializable;
import java.util.Map;

public interface Call extends Serializable {

    CallResult invoke(Map<String, ControllerRef<?>> controllers);
    
}
