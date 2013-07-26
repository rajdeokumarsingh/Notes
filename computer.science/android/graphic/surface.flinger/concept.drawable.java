/*
What is Drawable?
    abstraction of something can be drawn
        bitmap

    as the type of resource retrieved for drawing things to the screen;

    provides a generic API
        dealing with an underlying visual resource that may take a variety of forms.
        does NOT have any facility to receive UI events

    provides generic mechanisms for its client to interact with what is being drawn:
        {setBounds}
            MUST be called to tell the Drawable 
                1. where it is drawn 
                2. how large it should be.  

            A client can find the preferred size for some Drawables with
                {getIntrinsicHeight} 
                {getIntrinsicWidth}

         {getPadding} 
             information about how to frame content that is placed inside of them.
             For example, a Drawable that is intended to be the frame for a button
             widget would need to return padding that correctly places the label
             inside of itself.

         {setState} 
            tell the Drawable in which state it is to be drawn, such as 
            "focused", "selected", etc.
            Some drawables may modify their imagery based on the selected state.

         {setLevel} 
            allows the client to supply a single continuous controller 
            that can modify the Drawable is displayed, 
                such as a battery level or progress level.  

            Some drawables may modify their imagery based on the current level.

         <li> A Drawable can perform animations by calling back to its client
         through the {@link Callback} interface.  All clients should support this
         interface (via {@link #setCallback}) so that animations will work.  A
         simple way to do this is through the system facilities such as
         {@link android.view.View#setBackgroundDrawable(Drawable)} and
         {@link android.widget.ImageView}.
         </ul>

    Instance of Drawable:
        Bitmap: 
            PNG or JPEG image, etc

        Nine Patch: 
            extension to PNG format 
            allows to specify information about how to:
                stretch it 
                place things inside of it.

        Shape: 
            contains simple drawing commands 
            allowing it to resize better in some cases.

        Layers: 
            a compound drawable
            which draws multiple underlying drawables on top of each other.

        States: 
            a compound drawable 
            selects one of a set of drawables based on its state.
            for example, focus, selected

        Levels: 
            a compound drawable 
            selects one of a set of drawables based on its level.
            for example, progress bar

        Scale: 
            a compound drawable with a single child drawable, 
            whose overall size is modified based on the current level.

        For information and examples of creating drawable resources (XML or bitmap files that
        can be loaded in code), see <a href="{@docRoot}guide/topics/resources/drawable-resource.html">
*/




