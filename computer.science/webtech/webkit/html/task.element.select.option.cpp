================================================================================
实现功能下面功能：
    <p>下面的下拉框中应该默认显示三个选项，但一共有八个选项</p>
    <form action="" method="post">
        <select name="computer" size=3>
            <option>Apples
            <option>Oranges
            <option>Bananas
            <option>Grapes
            <option>Strawberries
        </select>
    </form>

当select element中如果有属性size=3时，将第一个option设置成selected状态。

================================================================================
背景关系：
select节点使用HTMLSelectElement类表示
option节点使用HTMLOptionElement类表示

html element是什么：
    HTMLOptionElement : public HTMLFormControlElement : public HTMLElement :
        public StyledElement : public Element : public ContainerNode : public Node

        Node : public EventTarget, public TreeShared<ContainerNode>, public ScriptWrappable


HTMLSelectElement创建后调用：
    1. parseMappedAttribute
        将size属性解析并保存下来

HTMLOptionElement被创建后：
    1. 系统将其加入到dom tree之后，
        HTMLOptionElement::insertedIntoTree(bool deep)会被调用


