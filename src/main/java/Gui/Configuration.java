package Gui;

import Data.DataNode;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Configuration {

    private List<DataNode> coldStart;
}
