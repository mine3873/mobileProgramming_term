package com.example.mobileprogramming_termproject.data;

import java.util.List;

public class NaverSearchResponse {
    public List<Item> items;

    public static class Item {
        public String title;
        public String link;
        public String image;
        public String lprice;
        public String brand;
    }
}
