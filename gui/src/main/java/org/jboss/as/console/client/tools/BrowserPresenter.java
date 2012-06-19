package org.jboss.as.console.client.tools;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import org.jboss.as.console.client.core.NameTokens;
import org.jboss.as.console.client.domain.model.SimpleCallback;
import org.jboss.as.console.client.shared.dispatch.DispatchAsync;
import org.jboss.as.console.client.shared.dispatch.impl.DMRAction;
import org.jboss.as.console.client.shared.dispatch.impl.DMRResponse;
import org.jboss.dmr.client.ModelNode;
import org.jboss.dmr.client.Property;

import java.util.ArrayList;
import java.util.List;

import static org.jboss.dmr.client.ModelDescriptionConstants.*;

/**
 * @author Heiko Braun
 * @date 6/15/12
 */
public class BrowserPresenter extends Presenter<BrowserPresenter.MyView, BrowserPresenter.MyProxy> {

    private final PlaceManager placeManager;
    private DispatchAsync dispatcher;

    @ProxyCodeSplit
    @NameToken(NameTokens.DMRBrowser)
    public interface MyProxy extends Proxy<BrowserPresenter>, Place {
    }

    public interface MyView extends View {
        void setPresenter(BrowserPresenter presenter);
        void updateChildrenTypes(ModelNode address, List<ModelNode> modelNodes);
        void updateChildrenNames(ModelNode address, List<ModelNode> modelNodes);
    }

    @Inject
    public BrowserPresenter(
            EventBus eventBus, MyView view, MyProxy proxy,
            PlaceManager placeManager, DispatchAsync dispatcher) {
        super(eventBus, view, proxy);

        this.placeManager = placeManager;
        this.dispatcher = dispatcher;
    }

    @Override
    protected void onBind() {
        super.onBind();
        getView().setPresenter(this);
    }


    @Override
    protected void onReset() {
        super.onReset();

        readChildrenTypes(new ModelNode().setEmptyList());
    }

    public void readChildrenTypes(final ModelNode address) {

        ModelNode operation  = new ModelNode();
        operation.get(ADDRESS).set(address);
        operation.get(OP).set(READ_CHILDREN_TYPES_OPERATION);

        dispatcher.execute(new DMRAction(operation), new SimpleCallback<DMRResponse>() {
            @Override
            public void onSuccess(DMRResponse dmrResponse) {
                final ModelNode response = dmrResponse.get();
                System.out.println(response);
                getView().updateChildrenTypes(address, response.get(RESULT).asList());
            }
        });
    }

    public void readChildrenNames(final ModelNode address) {

        final List<ModelNode> addressList = address.asList();
        ModelNode typeDenominator = null;
        List<ModelNode> actualAddress = new ArrayList<ModelNode>();
        int i=0;
        for(ModelNode path : addressList)
        {
            if(i<addressList.size()-1)
                actualAddress.add(path);
            else
                typeDenominator = path;

            i++;
        }

        ModelNode operation  = new ModelNode();
        operation.get(ADDRESS).set(actualAddress);
        operation.get(OP).set(READ_CHILDREN_NAMES_OPERATION);
        operation.get(CHILD_TYPE).set(typeDenominator.asProperty().getName());

        dispatcher.execute(new DMRAction(operation), new SimpleCallback<DMRResponse>() {
            @Override
            public void onSuccess(DMRResponse dmrResponse) {
                final ModelNode response = dmrResponse.get();
                System.out.println(response);
                getView().updateChildrenNames(address, response.get(RESULT).asList());
            }
        });

    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(getEventBus(), ToolsPresenter.TYPE_MainContent, this);
    }
}
