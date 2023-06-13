package ru.shafikova.UnpackingService.Services;

import ru.shafikova.UnpackingService.Models.Node;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class HackedObjectInputStream extends ObjectInputStream {

    public HackedObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        ObjectStreamClass resultClassDescriptor = super.readClassDescriptor();

        if (resultClassDescriptor.getName().equals("ru.shafikovs.ComressionService.Models.Node"))
            resultClassDescriptor = ObjectStreamClass.lookup(Node.class);

        return resultClassDescriptor;
    }
}