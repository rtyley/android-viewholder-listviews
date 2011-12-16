The aim is to reduce the boilerplate around writing your own ListAdapter class. With the library, you define two smaller classes, a 
ViewHolderFactory and a ViewHolder implementation.

Here's how you would set the adapter on your listView:

```java
listView.setAdapter(new ViewHoldingListAdapter<Foo>(fooList, viewInflatorFor(context, foo_list_item), new ViewHolderFactory<Foo>() {
    public ViewHolder<Foo> createViewHolderFor(View view) {
	return new FooViewHolder(view);
    }
}));
```

Your FooViewHolder just does the job of the ViewHolder - holds the references to the required Views, and populates those views with the relevant
fields from you Foo:

```java
public class FooViewHolder implements ViewHolder<Foo> {
    private final TextView fooName,fooDate;
    private final ImageView icon;

    public fooViewHolder(View v) {
	this.avatarSession = avatarSession;
	fooName = (TextView) v.findViewById(R.id.foo_item_name);
	fooDate = (TextView) v.findViewById(R.id.foo_item_date);
	icon = (ImageView) v.findViewById(R.id.foo_item_icon);
    }

    public void updateViewFor(Foo foo) {
	fooName.setText(foo.getName());
	fooDate.setText(foo.getTimeString());
	icon.setImageDrawable(iconBitmap);
    }
}
```