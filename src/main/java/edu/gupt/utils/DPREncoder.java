//package edu.gupt.utils;
//
//import ai.djl.inference.Predictor;
//import ai.djl.modality.nlp.qa.QAInput;
//import ai.djl.repository.zoo.Criteria;
//import ai.djl.repository.zoo.ZooModel;
//import ai.djl.translate.TranslateException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import javax.annotation.PostConstruct;
//import java.nio.file.Paths;
//
//@Component
//public class DPREncoder {
//
//    @Value("${ollama.dpr.model}")
//    private String modelPath;
//
//    private ZooModel<QAInput, float[]> model;
//
//    @PostConstruct
//    public void init() throws Exception {
//        Criteria<QAInput, float[]> criteria = Criteria.builder()
//                .setTypes(QAInput.class, float[].class)
//                .optModelPath(Paths.get(modelPath))
//                .build();
//        this.model = criteria.loadModel();
//    }
//
//    public float[] encode(String text) throws TranslateException {
//        try (Predictor<QAInput, float[]> predictor = model.newPredictor()) {
//            QAInput input = new QAInput(text, "");
//            return predictor.predict(input);
//        }
//    }
//}