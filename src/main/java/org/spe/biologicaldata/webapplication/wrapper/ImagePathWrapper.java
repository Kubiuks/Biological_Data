package org.spe.biologicaldata.webapplication.wrapper;

import lombok.Getter;
import lombok.Setter;

public class ImagePathWrapper {
    @Getter @Setter
    private String link;

    @Getter @Setter
    private String pathId;

    public ImagePathWrapper(String link, String pathId) {
        this.link = link;
        this.pathId = pathId;
    }

    public ImagePathWrapper(){}

}
