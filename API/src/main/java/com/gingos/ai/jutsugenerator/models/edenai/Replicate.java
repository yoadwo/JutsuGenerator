package com.gingos.ai.jutsugenerator.models.edenai;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Replicate{
    private String status;
    private List<Item> items;
    private double cost;
}
