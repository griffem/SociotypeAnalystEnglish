package org.socionicasys.analyst.web;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.socionicasys.analyst.ADocument;
import org.socionicasys.analyst.DocumentSection;
import org.socionicasys.analyst.model.AData;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class FileExport {
    
    public String getConfigText(ADocument document) {
        List<DocumentSection> sections = new ArrayList<DocumentSection>(document.getADataMap().keySet());
		Collections.sort(sections);
		for (int i = 0; i < sections.size(); i++) {
			DocumentSection section = sections.get(i);
			AData data = document.getADataMap().get(section);


        }
        return "";
    }

    public class Config {
        JsonObject configJson = new JsonObject();
        JsonArray analysisData = new JsonArray();
        
        public Config(String appVer, String title, String client, String experts) {
            configJson.addProperty("app_ver", appVer);
            configJson.addProperty("title", title);
            configJson.addProperty("client", client);
            configJson.addProperty("experts", experts);

            String dateString = DateFormat.getDateInstance().format(new Date());
            configJson.addProperty("data_created", dateString);
        }

        public void setComments(String comment) {
            if (configJson.has("comments")) {
                configJson.remove("comments");
            }

            configJson.addProperty("comments", comment);
        }

        public void addAnalysis(String text, String element, String marker, String comment) {
            JsonObject analysisEntry = new JsonObject();

            analysisEntry.addProperty("text", text);
            analysisEntry.addProperty("count", analysisData.size()+1);
            analysisEntry.addProperty("element", element);
            analysisEntry.addProperty("marker", marker);
            analysisEntry.addProperty("comment", comment);

            analysisData.add(analysisEntry);
        }

        public JsonObject getJson() {
            configJson.add("analysis", analysisData);
            
            return configJson;
        }
    }
}
