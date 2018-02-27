package com.erikalves.application.utils;

import com.erikalves.application.exceptions.ApplicationException;
import com.erikalves.application.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Util {

    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);
    private static final Gson GSON = new Gson();

    public static boolean isJSONValid(String jsonInString) {

        try {
            GSON.fromJson(jsonInString, Object.class);
            return true;
        } catch (com.google.gson.JsonSyntaxException ex) {
            return false;
        }
    }

    public static String readJsonFromRequest(HttpServletRequest request) throws ApplicationException {

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            LOGGER.error("Unable to read Json from Request!", e);
            throw new ApplicationException("Error while parsing json from request, please check format. Error Message:" + e.getMessage());

        }
    }

    public static java.sql.Date getCurrentDate()  {

        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        java.sql.Date date = java.sql.Date.valueOf(dateTime);
        return date;
    }

    public static Timestamp getCurrentTs() {

        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        Timestamp timestamp = Timestamp.valueOf(dateTime);
        return timestamp;
    }


    public static String StringfyId(Long id) {

        return Long.toString(id);
    }

    public static Long LongfyId(String str) {

        long number = Long.parseLong(str);
        return number;
    }

    public static String toJson(User object) {

        String json = new Gson().toJson(object);
        return json;
    }

    public static String toJson(Object object) {

        String json = new Gson().toJson(object);
        return json;
    }

    public static JSONArray toJsonArray(Object object) {

        String json = toJson(object);
        JSONArray array = new JSONArray(json);
        return array;
    }

    public static Gson getGson() {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
        return gson;
    }

    public static <T> List<T> iterableToCollection(Iterable<T> iterable){
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    public static void interestCalculation(User user){

        LOGGER.debug("User's current interest {} ", user.getUserInterest());
        Double updatedInterest = 1.0;
        if(null!=user) {
            if (user.getUserRisk().equalsIgnoreCase("B")) {
                updatedInterest = user.getUserInterest() * 1.1;
                user.setUserInterest(updatedInterest);
            } else if (user.getUserRisk().equalsIgnoreCase("C")) {
                updatedInterest = user.getUserInterest() * 1.2;
                user.setUserInterest(updatedInterest);
            }
        }
        else{
            throw new ApplicationException("User object is null");
        }
    }

    public static void main(String[] args) {

        User user = new User();
        user.setUserId(1l);
        user.setUserName("Erik Alves");
        user.setUserLimitCredit(new BigDecimal("100.00"));
        user.setUserRisk("B");
        user.setUserInterest(22.25);
        if (user.getUserRisk().equalsIgnoreCase("B")){
            user.setUserInterest(user.getUserInterest() *1.1);
        }
        else if (user.getUserRisk().equalsIgnoreCase("C")){
            user.setUserInterest(user.getUserInterest() *1.2);
        }
        LOGGER.debug("User {} ", user.toString());
        String json = getGson().toJson(user);
        LOGGER.debug("Json representation of a the created Entity {} ", json);

    }
}
