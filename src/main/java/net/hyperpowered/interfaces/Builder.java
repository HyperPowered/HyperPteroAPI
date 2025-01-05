package net.hyperpowered.interfaces;

import org.json.simple.JSONObject;

public interface Builder {

    JSONObject buildToJSON() throws IllegalArgumentException;

}
