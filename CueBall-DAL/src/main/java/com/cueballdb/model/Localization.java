package com.cueballdb.model;

import lombok.Data;


@Data
//@Entity
//@Table(name = "localization")
public class Localization extends BaseEntity {

    //@Column(name = "section")
    private String section;

    //@Column(name = "loc_key")
    private String key;

    //@Column(name = "local_text")
    private String localText;

    //@Column(name = "page_type")
    private String pageType;

    //@Column(name = "lang_code")
    private String langCode;

}
