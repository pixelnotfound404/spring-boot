package com.kunthea.phoneshop.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralUntilTest {
    @Test
    public void testToIntegerList(){
        //Given
        List<String> names= List.of("Thea","Leng","Both");
        //When
        List<Integer> list = GeneralUtils.toIntegerList(names);
        //Then
        //[4,4,4]


        assertEquals(3, list.size());
        assertEquals(4,list.get(0));
        assertEquals(4,list.get(1));
        assertEquals(4,list.get(2));
    }

    @Test
    public void testEvenNumber(){
        //Given
        List<Integer> num= List.of(10,3,6,11,23);
        //When
        List<Integer> list = GeneralUtils.getEvenNumber(num);
        //Then
        List<Integer> expected=List.of(10,6);
        assertEquals(expected,list);

    }
}
