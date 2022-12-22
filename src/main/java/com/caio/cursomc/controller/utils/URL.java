package com.caio.cursomc.controller.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

    public static List<Long> decodeIntList(String arg){
        return Arrays.asList(arg.split(",")).stream().map(valor -> Long.parseLong(valor)).collect(Collectors.toList());
    }

    public static String decodeParam(String arg){
        try{
            return URLDecoder.decode(arg, "UTF-8");
        }catch (UnsupportedEncodingException ex){
            return "";
        }
    }
}
