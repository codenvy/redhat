<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#if cheatsheet.title??>
        <title>${cheatsheet.title}</title>
    <#else>
        <title></title>
    </#if>
</head>
<body>
    <#if cheatsheet.intro.description??>
        <p>${cheatsheet.intro.description}</p>
    </#if>
    <ol>
        <#if cheatsheet.item??>
            <#list cheatsheet.item as item>
                <li>
                    <ul>
                        <#if item.skip??><li>Skip - ${item.skip?c}</li></#if>
                        <#if item.label??><li>Label - ${item.label}</li></#if>
                        <#if item.title??><li>Title - ${item.title}</li></#if>
                        <#if item.description??><li>Description - ${item.description}</li></#if>
                        <#if item.action??>
                            <li>Action -
                                <ul>
                                    <#if item.action.pluginId??><li>PluginID - ${item.action.pluginId}</li></#if>
                                    <#if item.action.class??><li>Class - ${item.action.class}</li></#if>
                                    <#if item.action.param1??><li>Param1 - ${item.action.param1}</li></#if>
                                    <#if item.action.param2??><li>Param2 - ${item.action.param2}</li></#if>
                                </ul>
                            </li>
                        </#if>
                        <#if item.command??>
                            <li>Command -
                            <#if item.command.required??><li>Required - ${item.command.required?c}</li></#if>
                            <#if item.command.returns??><li>Returns - ${item.command.returns}</li></#if>
                            <#if item.command.serialization??><li>Serialization - ${item.command.serialization}</li></#if>
                            </li>
                        </#if>
                        <#if item.subitem??>
                            <#list item.subitem as subitem>
                                <li>
                                    <ul>
                                        <#if subitem.skip??><li>Skip - ${subitem.skip?c}</li></#if>
                                        <#if subitem.label??><li>Label - ${subitem.label}</li></#if>
                                        <#if subitem.title??><li>Title - ${subitem.title}</li></#if>
                                        <#if subitem.description??><li>Description - ${subitem.description}</li></#if>
                                        <#if subitem.action??>
                                            <li>Action -
                                                <ul>
                                                    <#if subitem.action.pluginId??><li>PluginID - ${subitem.action.pluginId}</li></#if>
                                                    <#if subitem.action.class??><li>Class - ${subitem.action.class}</li></#if>
                                                    <#if subitem.action.param1??><li>Param1 - ${subitem.action.param1}</li></#if>
                                                    <#if subitem.action.param2??><li>Param2 - ${subitem.action.param2}</li></#if>
                                                </ul>
                                            </li>
                                        </#if>
                                        <#if subitem.command??>
                                            <li>Command -
                                                <ul>
                                                    <#if subitem.command.required??><li>Required - ${subitem.command.required?c}</li></#if>
                                                    <#if subitem.command.returns??><li>Returns - ${subitem.command.returns}</li></#if>
                                                    <#if subitem.command.serialization??><li>Serialization - ${subitem.command.serialization}</li></#if>
                                                </ul>
                                            </li>
                                        </#if>
                                    </ul>
                                </li>
                            </#list>
                        </#if>
                    </ul>
                </li>
            </#list>
        </#if>
    </ol>
</body>