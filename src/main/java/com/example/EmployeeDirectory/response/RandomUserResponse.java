package com.example.EmployeeDirectory.response;

import lombok.Data;
import java.util.List;

@Data
public class RandomUserResponse {
    private List<Result> results;

    @Data
    public static class Result {
        private Name name;
        private String email;
        private Picture picture;

        @Data
        public static class Name {
            private String title;
            private String first;
            private String last;
        }

        @Data
        public static class Picture {
            private String large;
            private String medium;
            private String thumbnail;
        }
    }
}
