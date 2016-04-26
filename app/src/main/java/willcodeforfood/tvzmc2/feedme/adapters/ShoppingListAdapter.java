package willcodeforfood.tvzmc2.feedme.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import willcodeforfood.tvzmc2.feedme.R;


public class ShoppingListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listHeader;
    private HashMap<String, List<String>> listChild;

    public ShoppingListAdapter(Context _context, List<String> _listHeader, HashMap<String, List<String>> _listChild){
        this.context = _context;
        this.listHeader = _listHeader;
        this.listChild = _listChild;
    }

    @Override
    public int getGroupCount() {
        return this.listHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listChild.get(this.listHeader.get(groupPosition))
                    .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listChild.get(this.listHeader.get(groupPosition))
                    .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.shopping_list_header, null);
        }

        TextView headerTextView = (TextView) convertView.findViewById(R.id.shoppingListHeader);
        headerTextView.setText(headerTitle);
        headerTextView.setTypeface(null, Typeface.BOLD);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition,final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition,childPosition);

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =  layoutInflater.inflate(R.layout.shopping_list_item, null);
        }

        TextView childTextView = (TextView) convertView.findViewById(R.id.shoppingListItem);
        childTextView.setText(childText);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
