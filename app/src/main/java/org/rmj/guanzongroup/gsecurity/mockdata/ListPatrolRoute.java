package org.rmj.guanzongroup.gsecurity.mockdata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.guanzongroup.gsecurity.pojo.itinerary.PatrolRoute;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Everything inside this class is mock data for demonstration purposes only...
 * Delete mockdata package after integrating Back-End APIs and retrieving actual
 * data needed for full operation...
 */
public class ListPatrolRoute {

    public static LiveData<List<PatrolRoute>> getPatrolRoute(){
        MutableLiveData<List<PatrolRoute>> itineraryList = new MutableLiveData<>();

        List<PatrolRoute> list = new ArrayList<>();
        Random random = new Random();

        for (int i = 1; i <= 15; i++) {
            PatrolRoute patrolRoute = new PatrolRoute();
            patrolRoute.setDescription("Visit " + getRandomPlace());
            patrolRoute.setCategory(getRandomCategory());
            patrolRoute.setWarehouse("Warehouse " + getRandomWarehouse());
            patrolRoute.setVisited(random.nextBoolean());

            list.add(patrolRoute);
        }

        itineraryList.postValue(list);
        return itineraryList;
    }

    private static String getRandomPlace() {
        String[] places = {"Paris", "New York", "Tokyo", "London", "Sydney"};
        Random random = new Random();
        return places[random.nextInt(places.length)];
    }

    private static String getRandomCategory() {
        String[] categories = {"Sightseeing", "Business", "Adventure", "Cultural", "Relaxation"};
        Random random = new Random();
        return categories[random.nextInt(categories.length)];
    }

    private static String getRandomWarehouse() {
        String[] warehouses = {"A", "B", "C", "D", "E"};
        Random random = new Random();
        return warehouses[random.nextInt(warehouses.length)];
    }
}
