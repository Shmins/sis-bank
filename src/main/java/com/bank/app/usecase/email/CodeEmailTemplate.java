package com.bank.app.usecase.email;

import org.springframework.stereotype.Service;

@Service
public class CodeEmailTemplate {

    public String getTemplate(String name, String code){
        return "<!DOCTYPE html>\r\n" + //
        "<html lang=\"en\">\r\n" + //
        "<link rel='preconnect' href='https://fonts.googleapis.com'>\r\n" + //
        "<link rel='preconnect' href='https://fonts.gstatic.com' crossorigin>\r\n" + //
        " <link href='https://fonts.googleapis.com/css2?family=Dela+Gothic+One&display=swap' rel='stylesheet'>\r\n" + //
        "    <head>\r\n" + //
        "        <style type=\"text/css\">\r\n" + //
        "            body{\r\n" + //
        "            margin: 0;\r\n" + //
        "            padding: 0;\r\n" + //
        "        }\r\n" + //
        "        .top{\r\n" + //
        "            position: relative;\r\n" + //
        "            display: flex;\r\n" + //
        "            align-items: center;\r\n" + //
        "            width: 100%;\r\n" + //
        "            height: 10%;\r\n" + //
        "            color: aliceblue;\r\n" + //
        "            background-color: #ff0096;\r\n" + //
        "        }\r\n" + //
        "        .top h3{\r\n" + //
        "            font-family: Arial, Helvetica, sans-serif;\r\n" + 
        "            font-weight: 400;\r\n" + //
        "            margin-left: 10%;\r\n" + //
        "        }\r\n" + //
        "        .body{\r\n" + //
        "            padding: 5%;\r\n" + //
        "            background-color: #fff;\r\n" + //
        "            width: 90%;\r\n" + //
        "           height: 50%;\r\n" + //
        "            position: relative;\r\n" + //
        "        }\r\n" + //
        "        .body .body_header{\r\n" + //
        "            margin-top: 5%;\r\n" + //
        "            border-bottom: 4px solid rgb(204, 204, 204);\r\n" + //
        "        }\r\n" + //
        "        .body .body_header p{\r\n" + //
        "            font-size: 20px;\r\n" + //
        "            font-family: Arial, Helvetica, sans-serif;\r\n" + //
        "            text-align: center;\r\n" + //
        "        }\r\n" + //
        "        .body .body_center{\r\n" + //
        "           text-align: center;\r\n" + //
        "            margin-top: 20px;\r\n" + //
        "        }\r\n" + //
        "        .body .body_center div{\r\n" + //
        "            display: inline;\r\n" + //
        "        }\r\n" + //
        "        .body .body_center h2{\r\n" + //
        "             font-family: Georgia, 'Times New Roman', Times, serif;\r\n" + //
        "             color: #ff0096;\r\n" + //
        "        }\r\n" + //
        "        .body .body_footer{\r\n" + //
        "            text-align: center;\r\n" + //
        "        }\r\n" + //
        "       .body .body_footer p:nth-child(2) b{\r\n" + //
        "            color: #ff0096;\r\n" + //
        "            font-family: Arial, Helvetica, sans-serif;\r\n" + //
        "            font-weight: bolder;\r\n" + //
        "        }\r\n" + //
        "        .footer{\r\n" + //
        "            height: 10%;\r\n" + //
        "            background-color: #fff;\r\n" + //
        "            display: flex;\r\n" + //
        "            align-items: center;\r\n" + //
        "            border-top: 1px solid rgb(53, 53, 53);\r\n" + //
        "        }\r\n" + //
        "        .footer h5{\r\n" + //
        "            margin-left: 10%;\r\n" + //
        "            font-family: 'Courier New', monospace;\r\n" + //
        "        }\r\n" + //
        "        </style>\r\n" + //
        "    </head>\r\n" + //
        "     \r\n" + //
        "    <body>\r\n" + //
        "        <div class=\"top\">\r\n" + //
        "            <h3>SwiftPay</h3>\r\n" + //
        "        </div>\r\n" + //
        "        <div class=\"body\">\r\n" + //
        "            <div class=\"body_header\">\r\n" + //
        "                <p>Bem vindo(a) "+ name +" a comunidade. <br> Recebemos sua solicitação.</p>\r\n" + //
        "            </div>\r\n" + //
        "            <div class=\"body_center\">\r\n" + //
        "                <div>\r\n" + //
        "                    <label>Código de verificação: </label>   \r\n" + //
        "                    <h2> "+ code +"</h2>\r\n" + //
        "                </div>\r\n" + //
        "            </div>\r\n" + //
        "            <div class=\"body_footer\">\r\n" + //
        "               <p>Caso não tenha se cadastrado, ignorar essa mensagem</p>\r\n" + //
        "               <p>Obrigado, equipe <b>SwiftPay.</b></p>\r\n" + //
        "            </div>\r\n" + //
        "        </div>\r\n" + //
        "        <div class=\"footer\">\r\n" + //
        "            <h5>@copyright SwiftPay</h5>\r\n" + //
        "        </div>\r\n" + //
        "    </body>\r\n" + //
        "</html>";
    }
}
