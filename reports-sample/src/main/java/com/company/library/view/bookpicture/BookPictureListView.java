package com.company.library.view.bookpicture;

import com.company.library.entity.BookPicture;
import com.company.library.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "book-pictures", layout = MainView.class)
@ViewController(id = "BookPicture.list")
@ViewDescriptor(path = "book-picture-list-view.xml")
@LookupComponent("bookPicturesDataGrid")
@DialogMode(width = "64em")
public class BookPictureListView extends StandardListView<BookPicture> {
}