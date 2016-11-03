package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map caloriesMap = new HashMap<LocalDateTime, Integer>();

        for (UserMeal meal:mealList
             ) {
                Integer dayCalories = (Integer) caloriesMap.get(meal.getDateTime().toLocalDate());
                if (dayCalories == null) {
                    caloriesMap.put(meal.getDateTime().toLocalDate(), meal.getCalories());
                } else {
                    caloriesMap.replace(meal.getDateTime().toLocalDate(), dayCalories, dayCalories +  meal.getCalories());
                }
        }

        List<UserMealWithExceed> mealListWithExceed = new ArrayList<UserMealWithExceed>();
        for (UserMeal meal:mealList) {
            LocalTime lt = meal.getDateTime().toLocalTime();
            if (lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0) {
                int cal = (int) caloriesMap.get(meal.getDateTime().toLocalDate());
                mealListWithExceed.add(new UserMealWithExceed(meal.getDateTime(),meal.getDescription(),meal.getCalories(),cal>caloriesPerDay));
            }
        }
        return mealListWithExceed;
    }
}
