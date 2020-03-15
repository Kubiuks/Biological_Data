package org.spe.biologicaldata.webapplication.wrapper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImageFile {

    @Getter @Setter
    private List<MultipartFile> file;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private String author;

    @Getter @Setter
    private String writtenDate;

    @Getter @Setter
    private String page;

    @Getter @Setter
    private String description;
}
