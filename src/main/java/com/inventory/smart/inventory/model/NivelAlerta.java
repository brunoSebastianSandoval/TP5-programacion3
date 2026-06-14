package com.inventory.smart.inventory.model;

public enum NivelAlerta {
    NORMAL,  // stock >= minimo
    BAJO,    // critico <= stock < minimo
    CRITICO  // stock < critico
}
