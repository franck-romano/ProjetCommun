package com.suricapp.tools;

/**
 * Created by maxence on 18/03/15.
 */
public class Variables {

    public final static String[] itemArray = { "Nouveau Message", "Vue Timeline", "Vue Carte","Mon profil", "Mes suiveurs", "Mes catégories", "Mes informations", "Déconnexion" };

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE= 100;
    public static final int GALLERY_IMAGE_ACTIVITY_REQUEST_CODE= 200;

    public static final String GETMAILFORUSER = "http://suricapp.esy.es/ws.php/d_user/user_email/";
    public static final String GETUSERWITHID = "http://suricapp.esy.es/ws.php/d_user/user_id/";
    public static final String GETPSEUDOFORUSER ="http://suricapp.esy.es/ws.php/d_user/user_pseudo/";
    public static final String POSTUSER = "http://suricapp.esy.es/ws.php/d_user/";
    public static final String POSTMESSAGE = "http://suricapp.esy.es/ws.php/d_message/";
    public static final String SURICAPPREFERENCES = "suricappprefs";
    public static final String GETMESSAGEFORCATEG = "http://suricapp.esy.es/ws.php/d_message/message_id_category_fk/";
    public static final int REQUEST_EXIT = 123;
    public static final int REQUEST_EXIT_GOOD= 250;


}
