package xyz.luan.console.parser.call;

import java.io.Serializable;
import java.util.Map;

import xyz.luan.console.parser.ControllerRef;

public interface Call extends Serializable {

    CallResult invoke(Map<String, ControllerRef<?>> controllers);
    
}
