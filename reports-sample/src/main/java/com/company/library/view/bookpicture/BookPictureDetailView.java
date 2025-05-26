package com.company.library.view.bookpicture;

import com.company.library.entity.BookPicture;
import com.company.library.view.main.MainView;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.view.*;

import java.io.ByteArrayInputStream;

@Route(value = "book-pictures/:id", layout = MainView.class)
@ViewController(id = "BookPicture.detail")
@ViewDescriptor(path = "book-picture-detail-view.xml")
@EditedEntityContainer("bookPictureDc")
public class BookPictureDetailView extends StandardDetailView<BookPicture> {

    @ViewComponent
    private JmixImage<Object> picture;

    @Subscribe
    public void onReady(final ReadyEvent event) {
        if (getEditedEntity().getPicture() != null) {
            picture.setSrc(new StreamResource(
                    getEditedEntity().getBookName(),
                    () -> new ByteArrayInputStream(getEditedEntity().getPicture())
                    ));
        }
    }
}