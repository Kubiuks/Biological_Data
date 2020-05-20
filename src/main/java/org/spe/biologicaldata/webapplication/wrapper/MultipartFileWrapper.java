package org.spe.biologicaldata.webapplication.wrapper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileWrapper {

    @Getter @Setter
    private MultipartFile file;
}
