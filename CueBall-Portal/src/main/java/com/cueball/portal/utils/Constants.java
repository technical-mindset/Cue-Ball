package com.cueball.portal.utils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */

public interface Constants {


    /**
     * Base URL For LIVE
     */
//	String RA_BASE_URL = "https://server.dawateislami.net";
//    String RA_COMMON_WEB_PORT = "/commonadmin";
//    String DONATION_PORT = "/CentralDonationAdmin";


    /**
     * Base URL For Local
     */
    String RA_BASE_URL = "http://localhost:8080";
    String RA_COMMON_WEB_PORT = "/commonadmin";
    String DONATION_PORT = "/CueBallPortal";


    /**
     * Base URL For Stage
     */
//    String RA_BASE_URL = "https://aws-stage.dibaadm.com";
//    String RA_COMMON_WEB_PORT = "/Admin-commonAdmin";
//    String DONATION_PORT = "/Admin-CentralDonationAdmin";





    /**
     * URL For Retrieving Header
     */
    String RA__AJAX_BASE_COMMON_URL = RA_BASE_URL+RA_COMMON_WEB_PORT;

    /**
     * URL Used In Localization
     */
    String SERVICE_BASE_URL = RA_BASE_URL + DONATION_PORT;


    String RA_BASE_SERVICE_TAG = RA_BASE_URL;

    /**
     * For Extra Filters Slice
     */
    String EXTRA_FILTERS = "extraFilters";
    String GENERIC_FILTER_LIST = "objectList";
    String GENERIC_OBJECT_NAME1 = "objectName";
    String GENERIC_OBJ1 = "obj";


    /**
     * For Language
     */
    String LANGUAGE_SERVICE = "/do/language/all";
    String USER_LANGUAGE_SERVICE = "/do/userLang/all";
    String LANGUAGE_SERVICE_URL = RA__AJAX_BASE_COMMON_URL + LANGUAGE_SERVICE;
    String USER_LANGUAGE_SERVICE_URL = RA__AJAX_BASE_COMMON_URL + USER_LANGUAGE_SERVICE;


    /**
      Base URL
     */
    String BASE = "/CueBallPortal";

    /**
     * User URLs
     */
    String RA_PAGE_USER_ADD_EDIT = "users/addEdit";
    String RA_PAGE_USER_VIEW_ALL = "users/viewAll";
    String RA_PAGE_USER_VIEW_ALL_DETAIL = "users/viewAllDetail";

    String RA_ROLE = "roles";

    String RA_PAGE_NUMBER = "pageNumber";
    String RA_PAGE_SIZE = "pageSize";
    String RA_TOTAL_PAGES = "totalPages";

    String RA_DTO = "dto";
    String RA_LIST = "dataList";

    /**
      Login URLs
     */
    String RA_ROOT_PAGE= "login/login";
    String RA_DASHBOARD="dashboard/dashboard";

    /**
     Legal Entity URLs
     */
    String RA_PAGE_LEGAL_ENTITY_ADD_EDIT = "legalEntity/addEdit";
    String RA_PAGE_LEGAL_ENTITY_VIEW_ALL = "legalEntity/viewAll";
    String RA_PAGE_LEGAL_ENTITY_VIEW_ALL_DETAIL = "legalEntity/viewAllDetail";

    /**
      Operating Unit URLs
     */
    String RA_PAGE_OPERATING_UNIT_ADD_EDIT = "operatingUnit/addEdit";
    String RA_PAGE_OPERATING_UNIT_VIEW_ALL = "operatingUnit/viewAll";
    String RA_PAGE_OPERATING_UNIT_VIEW_ALL_DETAIL = "operatingUnit/viewAllDetail";

    /**
      Room URLs
     */
    String RA_PAGE_ROOM_ADD_EDIT = "room/addEdit";
    String RA_PAGE_ROOM_VIEW_ALL = "room/viewAll";
    String RA_PAGE_ROOM_VIEW_ALL_DETAIL = "room/viewAllDetail";

    /**
      Inventory URLs
     */
    String RA_PAGE_INVENTORY_ADD_EDIT = "inventory/addEdit";
    String RA_PAGE_INVENTORY_VIEW_ALL = "inventory/viewAll";
    String RA_PAGE_INVENTORY_VIEW_ALL_DETAIL = "inventory/viewAllDetail";

    /**
      Campaign URLs
     */
    String RA_PAGE_CAMPAIGN_ADD_EDIT = "campaign/addEdit";
    String RA_PAGE_CAMPAIGN_VIEW_ALL = "campaign/viewAll";
    String RA_PAGE_CAMPAIGN_VIEW_ALL_DETAIL = "campaign/viewAllDetail";

    /**
     Transaction URLs
     */
    String RA_PAGE_TRANSACTION_VIEW_ALL = "transaction/viewAll";
    String RA_PAGE_TRANSACTION_VIEW_ALL_DETAIL = "transaction/viewAllDetail";

    /**
      Room Category URLs
     */
    String RA_PAGE_ROOM_CATEGORY_ADD_EDIT = "roomCategory/addEdit";
    String RA_PAGE_ROOM_CATEGORY_VIEW_ALL = "roomCategory/viewAll";
    String RA_PAGE_ROOM_CATEGORY_VIEW_ALL_DETAIL = "roomCategory/viewAllDetail";

    /**
      Inventory Category URLs
     */
    String RA_PAGE_INVENTORY_CATEGORY_ADD_EDIT = "inventoryCategory/addEdit";
    String RA_PAGE_INVENTORY_CATEGORY_VIEW_ALL = "inventoryCategory/viewAll";
    String RA_PAGE_INVENTORY_CATEGORY_VIEW_ALL_DETAIL = "inventoryCategory/viewAllDetail";

    /**
      Variant URLs
     */
    String RA_PAGE_VARIANT_ADD_EDIT = "variant/addEdit";
    String RA_PAGE_VARIANT_VIEW_ALL = "variant/viewAll";
    String RA_PAGE_VARIANT_VIEW_ALL_DETAIL = "variant/viewAllDetail";

    /**
     Game URLs
     */
    String RA_PAGE_GAME_ADD_EDIT = "game/addEdit";
    String RA_PAGE_GAME_VIEW_ALL = "game/viewAll";
    String RA_PAGE_GAME_VIEW_ALL_DETAIL = "game/viewAllDetail";

    /**
     Booking URLs
     */
    String RA_PAGE_BOOKING_ADD_EDIT = "booking/addEdit";
    String RA_PAGE_BOOKING_VIEW_ALL = "booking/viewAll";
    String RA_PAGE_BOOKING_VIEW_ALL_DETAIL = "booking/viewAllDetail";


    /**
     Localization URLs
     */
    String RA_PAGE_LOCALIZATION_ADD_EDIT = "localization/addEdit";
    String RA_PAGE_LOCALIZATION_VIEW_ALL = "localization/viewAll";
    String RA_PAGE_LOCALIZATION_VIEW_ALL_DETAIL = "localization/viewAllDetail";

    /**
     For Validation
     */
    String RA_EDIT_MESSAGE="This Value is Already Exist";
    String RA_DEFAULT_BANK_MESSAGE="Kindly un select the Bank from connected Operating Unit first ! ";
    String RA_DEFAULT_CURRENCY_MESSAGE="Kindly un select the Currency from connected Operating Unit first ! ";
    String RA_EMPTY_MESSAGE="must not be Empty";
    String RA_LENGTH_STRING="No. of Characters must be between 4 to 255";
    String RA_LENGTH_STRING_10="No. of Characters must be less than 10";
    String RA_LENGTH_STRING_20="No. of Characters must be less than 20";
    String RA_LENGTH_STRING_100="must not exceed 100 characters";
    String RA_LENGTH_STRING_500="No. of Characters must be between 3 to 500";
    String RA_LENGTH_STRING_URL="Url must be like https://dawateislami.net/ ";
    String RA_IMAGE_LENGTH_STRING_URL="Url must be like https://example.net/example.webp or .png or .jpg or .jpeg and () {} [] not allowed";
    String RA_REGEX_STRING_URL="Url must be like roman-url or romanurl ";
    String RA_LENGTH_STRING_MAX="Maximum Limit is 500";
    String RA_REGEX_URL="\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;'()]*[-a-zA-Z0-9+&@#/%=~_|'()]$";
    String RA_IMAGE_REGEX_URL="\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;']*\\.(png|webp|jpg|jpeg)\\b";
    String RA_REGEX_CURRENCY="^[A-Z]{3}$";
    String RA_REGEX_ROMAN_URL="^[a-z-]+$";
    int RA_4=4;
    int RA_5=5;
    int RA_10=10;
    int RA_20=20;
    int RA_255=255;
    int RA_500=500;
    int MAX_PER_PAGE = 10;
    int DEFAUT_START_PAGENUMBER = 1;

    /**
     For Excel Report
     */
    public final static String[] COLUMNS = {
            "Id",
            "Created Date",
            "Name",
            "Email",
            "Phone",
            "Type",
            "Status",
            "Order ID",
            "Bank Name",
            "Campaign Title",
            "Operating Unit Title",
            "Currency",
            "Country",
            "Actual Amount",
            "Converted Amount",
            "Total Amount",
            "Marketing Source",
            "Advertisement",
            "Bank Transaction Id",
            "Bank Transaction Status",
            "Bank Description",
            "Bank Response Code"
    };






}
