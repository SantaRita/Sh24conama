package com.csi_ti.itaca.custom.general.server.jdbc;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class AccesoPL {

    /**
     * Cualquier parametro que se desa ocultar en las trazas se ha de informar aqui. Son regexp's. Y Siempre en minusculas.
     * ecg20130306 http://mantis.srvcsi.com/view.php?id=26312#c139813
     */
    static String[] ocultarINs= { ".*pwd.*", ".*pass.*" }; //siempre en minusculas

    /**
     * Produce en la trazas un log de la llamada PL con la funcion y sus parametros.
     * Los valores de algunos parametros (que coinciden con los regexps de ocultarINs) se expresan como "(hidden)"
     * @param callQuery
     * @param sIN
     * @param pIN
     */
    protected void logCall(String callQuery, String[] sIN, Object[] pIN) {
        StringBuffer sB=new StringBuffer();
        sB.append("callQuery:").append(callQuery).append(", parametros:");
        boolean hideIN=false;
        for (int i=0; i<sIN.length; i++) {
            hideIN=false;
            for (String s: ocultarINs) {
                if (sIN[i].toLowerCase().matches(s)) {
                    hideIN=true;
                    break;
                }
            }
            if (hideIN) {
                //se ha de ocultar
                sB.append(sIN[i]).append("=").append("(hidden)");
            }
            else {
                //normal: se loggea
                sB.append(sIN[i]).append("=").append(pIN[i]);
            }
            if (i<sIN.length-1)
                sB.append(", ");
        }
        //log.debug(sB.toString());
    }

    public static void main(String[] args) throws Exception {
        String[] sIN= { "hola", "Lola", "mi_UsUario", "esPass", "pwd_text", "" };
        Object[] pIN= { "1111", "2222", "3333333333", "444444", "55555555", "6" };
        String callQuery="hola query";

        AccesoPL a=new AccesoPL();
        a.logCall(callQuery, sIN, pIN);

    }

}
