package com.suricapp.tools;

/**
 * Created by maxence on 18/03/15.
 */
public class Variables {

    //public final static String[] itemArray = { "Nouveau Message", "Vue Timeline", "Vue Carte","Mon profil", "Mes suiveurs", "Mes catégories", "Mes informations", "Déconnexion" };
    public final static String[] itemArray = { "Nouveau Message", "Autour de moi", "Carte des messages","Mon profil","Mes informations" , "Mes préférences","Déconnexion" };
    //public final static String[] itemArray = { "Nouveau Message", "Autour de vous", "Carte des messages","Mon profil", "Mes préférences","Déconnexion" };

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE= 100;
    public static final int GALLERY_IMAGE_ACTIVITY_REQUEST_CODE= 200;

    public static final String PUTMESSAGE = "http://vps53670.ovh.net/~suricapp/ws.php/d_message/";
    public static final String PUTUSER = "http://vps53670.ovh.net/~suricapp/ws.php/d_user/";
    public static final String GETMAILFORUSER = "http://vps53670.ovh.net/~suricapp/ws.php/d_user/user_email/";
    public static final String GETUSERWITHID = "http://vps53670.ovh.net/~suricapp/ws.php/d_user/user_id/";
    public static final String GETMULTIPLEUSERWITHID ="http://vps53670.ovh.net/~suricapp/wsa.php/d_user/?user_id[in]=";
    public static final String GETPSEUDOFORUSER ="http://vps53670.ovh.net/~suricapp/ws.php/d_user/user_pseudo/";
    public static final String POSTUSER = "http://vps53670.ovh.net/~suricapp/ws.php/d_user/";
    public static final String POSTMESSAGE = "http://vps53670.ovh.net/~suricapp/ws.php/d_message/";
    public static final String POSTCOMMENT = "http://vps53670.ovh.net/~suricapp/ws.php/d_comment/";

    public static final String SURICAPPREFERENCES = "suricappprefs";

    public static final String GETMESSAGEFROMUSER ="http://vps53670.ovh.net/~suricapp/ws.php/d_message/message_id_user_fk/";
    public static final String ORDERBYDATEDESC ="?by=message_date&order=desc";
    public static final String GETCOMMENTFROMMESSAGEID="http://vps53670.ovh.net/~suricapp/ws.php/d_comment/comment_message_id_fk/";
    public static final String ORDERBYCOMMENTDATEASC="?by=comment_date&order=asc";
    public static final int REQUEST_EXIT = 123;
    public static final int REQUEST_EXIT_GOOD= 250;
    public static final int REQUESTLOADMESSAGE=2000;
    public static final int REQUEST_CODE_MESSAGE_ACTIVITY=2001;
    public static final int REQUEST_CODE_CATEGORY_ACTIVITY=2002;


}
