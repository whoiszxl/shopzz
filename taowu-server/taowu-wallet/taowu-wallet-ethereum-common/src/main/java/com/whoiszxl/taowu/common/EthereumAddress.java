package com.whoiszxl.taowu.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EthereumAddress {

    private String address;

    private String keystoreName;

    private String mnemonic;
}
