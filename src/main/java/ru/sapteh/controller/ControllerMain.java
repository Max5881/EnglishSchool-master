package ru.sapteh.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.dao.Dao;
import ru.sapteh.model.Product;
import ru.sapteh.service.ProductService;

;import java.util.List;


public class ControllerMain {


    private final ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    private FlowPane flowPane;

    public void initialize(){

        initData();

        flowPane.setAlignment(Pos.TOP_LEFT);
        flowPane.setHgap(25);
        flowPane.setVgap(10);

        for (Product product : products) {
            flowPane.getChildren().add(getNode(product.getMainImagePath(),product.getTitle(),product.getCost()));
        }
    }

    private void initData() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Dao<Product,Integer> productService = new ProductService(factory);

        List<Product> list = productService.readByAll();
        products.addAll(list);

    }

    private Node getNode(String url, String name, double cost){
        AnchorPane pane = new AnchorPane();

        Image image = new Image(url);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(300);
        imageView.setFitWidth(200);

        pane.getChildren().add(imageView);

        Label label = new Label();
        label.setText(String.format("%s \n %.0f рублей",name,cost));
        label.setLayoutY(300);

        pane.getChildren().add(label);


        return pane;
    }

}

