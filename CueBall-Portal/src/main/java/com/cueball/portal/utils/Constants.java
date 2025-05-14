package com.cueball.portal.utils;


import java.util.Arrays;
import java.util.List;

/**
 *
 */

public interface Constants {


    /**
     * Base URL For LIVE
     */
//	String RA_BASE_URL = "https://server.dawateislami.net";
//    String PORT = "/CentralDonationAdmin";


    /**
     * Base URL For Local
     */
//    String RA_BASE_URL = "http://localhost:8081";
//    String PORT = "/CueBallPortal";


    /**
     * Base URL For Stage
     */
    String RA_BASE_URL = "http://3.145.74.161:8080";
    String PORT = "/CueBallPortal";






    /**
     * For Extra Filters Slice
     */
    String EXTRA_FILTERS = "extraFilters";


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
     TUCK SHOP BASE PATHS
     */
    String RA_PAGE_TUCK_SHOP_VIEW_ALL = "tuckShop/viewAll";
    String RA_PAGE_TUCK_SHOP_VIEW_ALL_DETAIL = "tuckShop/viewAllDetail";

    /**
     TUCK SHOP BASE PATHS
     */
    String RA_PAGE_TUCK_SHOP_SUB_MENU_VIEW_ALL = "tuckShop/tuckShopItems";
    String RA_PAGE_TUCK_SHOP_SUB_MENU_VIEW_ALL_DETAIL = "tuckShop/tuckShopItemsDetail";


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
     Restaurant URLs
     */
    String RA_PAGE_RESTAURANT_ADD_EDIT = "restaurant/addEdit";
    String RA_PAGE_RESTAURANT_VIEW_ALL = "restaurant/viewAll";
    String RA_PAGE_RESTAURANT_VIEW_ALL_DETAIL = "restaurant/viewAllDetail";

    /**
     Booking URLs
     */
    String RA_PAGE_BOOKING_ADD_EDIT = "booking/addEdit";
    String RA_PAGE_BOOKING_VIEW_ALL = "booking/viewAll";
    String RA_PAGE_BOOKING_VIEW_ALL_DETAIL = "booking/viewAllDetail";

    /**
     Booking Customer Check-Out URLs
     */
    String RA_PAGE_CUSTOMER_CHECKOUT_VIEW_ALL = "customer/viewAll";
    String RA_PAGE_CUSTOMER_CHECKOUT_VIEW_ALL_DETAIL = "customer/viewAllDetail";


    /**
     Tasks URLs
     */
    String RA_PAGE_TASK_ADD_EDIT = "task/addEdit";
    String RA_PAGE_TASK_VIEW_ALL = "task/viewAll";
    String RA_PAGE_MY_TASK_VIEW_ALL = "task/ViewAll";
    String RA_PAGE_TASK_VIEW_ALL_DETAIL = "task/viewAllDetail";


    /**
     Localization URLs
     */
    String RA_PAGE_TUCK_SHOP_REPORT_VIEW_ALL = "reports/tuckBuyingReport/viewAll";
    String RA_PAGE_TUCK_SHOP_REPORT_VIEW_ALL_DETAIL = "reports/tuckBuyingReport/viewAllDetail";

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
    String RA_REGEX_URL="\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=_|!:,.;'()]*[-a-zA-Z0-9+&@#/%=_|'()]$";
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

    String RA_SWEET_ALERT_SUCCESS = "The status has been updated successfully.";
    String RA_SWEET_ALERT_FAILED = "An error occurred while updating the status.";

    String RA_SWEET_ALERT_DELETE_SUCCESS = "Deleted successfully.";
    String RA_SWEET_ALERT_DELETE_FAILED = "An error occurred while Deleting.";

    /**
     For Excel Report
     */
    public final static String[] COLUMNS = {
            "ID",             // ID
            "Created Date",   // Created Date
            "Booking",        // Booking
            "Customer",       // Customer
            "Email",          // Email
            "Contact",        // Contact
            "Category",       // Category
            "Room",      // Room Name
            "Time In",        // Time In
            "Time Out",       // Time Out
            "Check In",       // Check In
            "Check Out",      // Check Out
            "Total time",     // Total time
            "Room Charges",   // Room Charges
            "Total Charges"   // Total Charges
    };

    List<String> shifts = Arrays.asList("Morning", "Night");


    public final static String[] COLUMNS_TUCK_BUYING = {
            "ID",             // ID
            "Created Date",   // Created Date
            "Item Name",        // Item Name
            "Quantity",       // Quantity
            "Price",          // Price
    };









}