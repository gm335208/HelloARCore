package com.examples.knightcube.helloar;

import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {

    private ArFragment arFragment;
    ModelRenderable redSphereRenderable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arfragment);

        //Placing a sphere shape renderable using MaterialFactory
//        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
//            final Anchor anchor = hitResult.createAnchor();
//            MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.RED))
//                    .thenAccept(material -> redSphereRenderable = ShapeFactory.makeSphere(0.1f, new Vector3(0.0f, 0.15f, 0.0f), material))
//                    .exceptionally(throwable -> {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                        builder.setMessage(throwable.getMessage()).show();
//                        return null;
//                    });
//            addModelToScene(anchor, redSphereRenderable);
//        });

        //Place a poly Asset
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            final Anchor anchor = hitResult.createAnchor();
            ModelRenderable.builder()
                    .setSource(MainActivity.this, Uri.parse("Motorcycle.sfb"))
                    .build()
                    .thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable))
                    .exceptionally(throwable -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(throwable.getMessage()).show();
                        return null;
                    });
        });
    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {

        AnchorNode anchorNode = new AnchorNode(anchor);

        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);

        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }
}
