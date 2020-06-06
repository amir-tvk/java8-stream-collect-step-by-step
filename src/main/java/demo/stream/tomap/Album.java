package demo.stream.tomap;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Album {
    private String artist;
    private String name;
    private int sale;
}
