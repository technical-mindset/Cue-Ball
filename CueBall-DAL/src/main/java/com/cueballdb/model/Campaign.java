package com.cueballdb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
//@Entity
//@Table(name = "campaign")
public class Campaign extends BaseEntity {

    //@Column(name="title")
    private String title;

    //@Column(name="description")
    private String description;

    //@Column(name="image_url")
    private String imageUrl;

    //@Column(name="icon_url")
    private String iconUrl;

    //@Column(name="banner_title")
    private String bannerTitle;

    //@Column(name="banner_description")
    private String bannerDescription;

    //@Column(name="roman_url")
    private String romanUrl;

    //@Column(name="sadqa")
    private boolean sadqa;

    //@Column(name="zakat")
    private boolean zakat;

    //@Column(name="start_date")
    private Date startDate;

    //@Column(name="end_date")
    private Date endDate;

    //@Column(name = "life_time")
    private boolean lifeTime;

    //@Column(name = "show_home")
    private boolean showHome;

    //@Column(name = "root")
    private boolean root;

    // bi-directional many-to-one association to OperatingUnit
    @ManyToOne
    @JoinColumn(name = "operating_unit_id")
    private OperatingUnit operatingUnit;

    private int legalEntityId; // does not establish relation with RoomCategory but for track-record

}
