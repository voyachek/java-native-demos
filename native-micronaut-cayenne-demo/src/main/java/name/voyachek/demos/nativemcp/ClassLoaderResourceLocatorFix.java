package name.voyachek.demos.nativemcp;

import org.apache.cayenne.CayenneRuntimeException;
import org.apache.cayenne.ConfigurationException;
import org.apache.cayenne.di.ClassLoaderManager;
import org.apache.cayenne.di.Inject;
import org.apache.cayenne.resource.Resource;
import org.apache.cayenne.resource.ResourceLocator;
import org.apache.cayenne.resource.URLResource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

public class ClassLoaderResourceLocatorFix implements ResourceLocator {

    private ClassLoaderManager classLoaderManager;

    public ClassLoaderResourceLocatorFix(@Inject ClassLoaderManager classLoaderManager) {
        this.classLoaderManager = classLoaderManager;
    }

    @Override
    public Collection<Resource> findResources(String name) {
        final Collection<Resource> resources = new ArrayList<>(3);

        final Enumeration<URL> urls;
        try {
            urls = classLoaderManager.getClassLoader(name).getResources(name);
        } catch (IOException e) {
            throw new ConfigurationException("Error getting resources for ");
        }

        while (urls.hasMoreElements()) {
            resources.add(new URLResourceFix(urls.nextElement()));
        }

        return resources;
    }

    private class URLResourceFix extends URLResource {

        URLResourceFix(URL url) {
            super(url);
        }

        @Override
        public Resource getRelativeResource(String relativePath) {
            try {
                String url = getURL().toString();
                url = url.substring(0, url.lastIndexOf("/") + 1) + relativePath;

                return new URLResource(new URI(url).toURL());
            } catch (MalformedURLException | URISyntaxException e) {
                throw new CayenneRuntimeException(
                        "Error creating relative resource '%s' : '%s'",
                        e,
                        getURL(),
                        relativePath);
            }
        }

    }

}
