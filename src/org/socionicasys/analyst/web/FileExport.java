package org.socionicasys.analyst.web;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.BadLocationException;

import org.socionicasys.analyst.ADocument;
import org.socionicasys.analyst.DocumentSection;
import org.socionicasys.analyst.model.AData;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class FileExport {
    
    public String getConfigText(ADocument document) {
        Config config = new Config("1.0", "", "", "");

        Map<DocumentSection, AData> analysisMap = document.getADataMap();

        for (DocumentSection section : analysisMap.keySet()) {
            AData segment = analysisMap.get(section);

            int textLength = section.getEndOffset()-section.getStartOffset();

            try {
                String text = document.getText(section.getStartOffset(), textLength);

                config.addAnalysis(text, segment);
            } catch (BadLocationException e) {
                continue;
            }
        }

        return config.getJson().getAsString();
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

        public void setAnalysisComments(String comment) {
            if (configJson.has("comments")) {
                configJson.remove("comments");
            }

            configJson.addProperty("comments", comment);
        }

        public void addAnalysis(String text, AData analysis) {
            JsonObject analysisEntry = new JsonObject();

            analysisEntry.addProperty("text", text);
            analysisEntry.addProperty("count", analysisData.size()+1);
            analysisEntry.addProperty("aspectOne", analysis.getAspect());
            analysisEntry.addProperty("aspectTwo", analysis.getSecondAspect());
            analysisEntry.addProperty("sign", analysis.getSign());
            analysisEntry.addProperty("modifier", analysis.getModifier());
            analysisEntry.addProperty("dichotomy", analysis.getFD());
            analysisEntry.addProperty("blocks", analysis.getBlocks());

            analysisEntry.addProperty("comment", analysis.getComment());

            analysisData.add(analysisEntry);
        }

        public JsonObject getJson() {
            configJson.add("analysis", analysisData);
            
            return configJson;
        }
    }
}
