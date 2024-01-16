package ltd.rymc.form.residence.configs;

import space.arim.dazzleconf.annote.ConfKey;
import space.arim.dazzleconf.annote.SubSection;

public interface Language {

    @ConfKey("reload")
    String reload();

    @SubSection
    @ConfKey("forms")
    Forms forms();

    interface Forms {

        @SubSection
        @ConfKey("main")
        Main main();

        interface Main {
            @ConfKey("title")
            String title();

            @ConfKey("content")
            String content();

            @SubSection
            @ConfKey("buttons")
            Buttons buttons();

            interface Buttons {
                @ConfKey("teleport")
                String teleport();

                @ConfKey("manage")
                String manage();

                @ConfKey("create")
                String create();

                @ConfKey("tool")
                String tool();

            }
        }

        @SubSection
        @ConfKey("teleport")
        Teleport teleport();

        interface Teleport {
            @ConfKey("title")
            String title();

            @ConfKey("dropdown")
            String dropdown();

            @ConfKey("input1")
            String input1();

            @ConfKey("input2")
            String input2();

            @ConfKey("choose")
            String choose();
        }

        @SubSection
        @ConfKey("manage")
        Manage manage();
        interface Manage {
            @SubSection
            @ConfKey("select")
            Select select();

            interface Select {
                @ConfKey("title")
                String title();

                @ConfKey("dropdown")
                String dropdown();

                @ConfKey("choose")
                String choose();
            }

            @SubSection
            @ConfKey("main")
            Main main();

            interface Main {
                @ConfKey("title")
                String title();

                @ConfKey("content")
                String content();

                @SubSection
                @ConfKey("buttons")
                Buttons buttons();

                interface Buttons {
                    @ConfKey("set")
                    String set();

                    @ConfKey("player-set")
                    String playerSet();

                    @ConfKey("trusted-player")
                    String trustedPlayer();

                    @ConfKey("teleport-set")
                    String teleportSet();

                    @ConfKey("kick")
                    String kick();

                    @ConfKey("sensitive")
                    String sensitive();
                }
            }

            @SubSection
            @ConfKey("set")
            Set set();

            interface Set {
                @ConfKey("title")
                String title();

            }

            @SubSection
            @ConfKey("player-set")
            PlayerSet playerSet();

            interface PlayerSet {

                @SubSection
                @ConfKey("select")
                Select select();

                interface Select {
                    @ConfKey("title")
                    String title();

                    @ConfKey("dropdown")
                    String dropdown();

                    @ConfKey("input1")
                    String input1();

                    @ConfKey("input2")
                    String input2();

                    @ConfKey("choose")
                    String choose();
                }


                @SubSection
                @ConfKey("set")
                Set set();

                interface Set {
                    @ConfKey("title")
                    String title();

                }
            }

            @SubSection
            @ConfKey("trusted-player")
            TrustedPlayer trustedPlayer();

            interface TrustedPlayer {
                @SubSection
                @ConfKey("main")
                Main main();

                interface Main {
                    @ConfKey("title")
                    String title();

                    @ConfKey("content")
                    String content();

                    @ConfKey("button")
                    String button();
                }

                @SubSection
                @ConfKey("change")
                Change change();

                interface Change {
                    @ConfKey("title")
                    String title();

                    @ConfKey("dropdown")
                    String dropdown();

                    @ConfKey("input1")
                    String input1();

                    @ConfKey("input2")
                    String input2();

                    @ConfKey("toggle")
                    String toggle();

                    @ConfKey("choose")
                    String choose();
                }
            }

            @SubSection
            @ConfKey("teleport-set")
            TeleportSet teleportSet();
            interface TeleportSet {
                @ConfKey("title")
                String title();

                @ConfKey("content")
                String content();

                @SubSection
                @ConfKey("buttons")
                Buttons buttons();

                interface Buttons {
                    @ConfKey("set")
                    String set();

                    @ConfKey("back")
                    String back();
                }
            }

            @SubSection
            @ConfKey("kick")
            Kick kick();

            interface Kick {
                @ConfKey("title")
                String title();

                @ConfKey("dropdown")
                String dropdown();

                @ConfKey("input1")
                String input1();

                @ConfKey("input2")
                String input2();

                @ConfKey("choose")
                String choose();
            }

            @SubSection
            @ConfKey("no-permission")
            NoPermission noPermission();

            interface NoPermission {
                @ConfKey("title")
                String title();

                @ConfKey("content")
                String content();

                @ConfKey("button")
                String button();

            }

            @SubSection
            @ConfKey("sensitive")
            Sensitive sensitive();

            interface Sensitive {
                @SubSection
                @ConfKey("main")
                Main main();

                interface Main {
                    @ConfKey("title")
                    String title();

                    @ConfKey("content")
                    String content();

                    @SubSection
                    @ConfKey("buttons")
                    Buttons buttons();

                    interface Buttons {
                        @ConfKey("rename")
                        String rename();

                        @ConfKey("expand")
                        String expand();

                        @ConfKey("give")
                        String give();

                        @ConfKey("remove")
                        String remove();

                    }
                }

                @SubSection
                @ConfKey("rename")
                Rename rename();

                interface Rename {
                    @ConfKey("title")
                    String title();

                    @ConfKey("input1")
                    String input1();

                    @ConfKey("input2")
                    String input2();

                    @ConfKey("input3")
                    String input3();

                    @ConfKey("input4")
                    String input4();
                }

                @SubSection
                @ConfKey("expand")
                Expand expand();

                interface Expand {
                    @ConfKey("title")
                    String title();

                    @ConfKey("dropdown")
                    String dropdown();

                    @ConfKey("input1")
                    String input1();

                    @ConfKey("input2")
                    String input2();

                    @ConfKey("toggle")
                    String toggle();
                }

                @SubSection
                @ConfKey("give")
                Give give();

                interface Give {

                    @ConfKey("title")
                    String title();

                    @ConfKey("input1")
                    String input1();

                    @ConfKey("input2")
                    String input2();

                    @ConfKey("input3")
                    String input3();

                    @ConfKey("input4")
                    String input4();

                }

                @SubSection
                @ConfKey("remove")
                Remove remove();

                interface Remove {

                    @ConfKey("title")
                    String title();

                    @ConfKey("input1")
                    String input1();

                    @ConfKey("input2")
                    String input2();

                    @ConfKey("input3")
                    String input3();

                    @ConfKey("input4")
                    String input4();

                }

                @SubSection
                @ConfKey("confirm")
                Confirm confirm();

                interface Confirm {

                    @ConfKey("content")
                    String content();

                    @SubSection
                    @ConfKey("buttons")
                    Buttons buttons();

                    interface Buttons {
                        @ConfKey("accept")
                        String accept();

                        @ConfKey("deny")
                        String deny();
                    }
                }
            }
        }

        @SubSection
        @ConfKey("create")
        Create create();
        interface Create {
            @SubSection
            @ConfKey("main")
            Main main();

            interface Main {
                @ConfKey("title")
                String title();

                @SubSection
                @ConfKey("content")
                Content content();

                interface Content {
                    @ConfKey("title")
                    String title();

                    @ConfKey("not-create")
                    String notCreate();

                    @ConfKey("coordinates1")
                    String coordinates1();

                    @ConfKey("coordinates2")
                    String coordinates2();

                    @ConfKey("world")
                    String world();

                    @ConfKey("unknown")
                    String unknown();

                    @ConfKey("x")
                    String x();

                    @ConfKey("y")
                    String y();

                    @ConfKey("z")
                    String z();

                    @ConfKey("cost")
                    String cost();

                }

                @SubSection
                @ConfKey("buttons")
                Buttons buttons();

                interface Buttons {
                    @ConfKey("auto")
                    String auto();

                    @ConfKey("select")
                    String select();

                    @ConfKey("manual")
                    String manual();

                    @ConfKey("expend")
                    String expend();

                    @ConfKey("create")
                    String create();
                }
            }

            @SubSection
            @ConfKey("select")
            Select select();

            interface Select {

                @ConfKey("title")
                String title();

                @ConfKey("input-x1")
                String inputX1();

                @ConfKey("input-x2")
                String inputX2();

                @ConfKey("input-y1")
                String inputY1();

                @ConfKey("input-y2")
                String inputY2();

                @ConfKey("input-z1")
                String inputZ1();

                @ConfKey("input-z2")
                String inputZ2();
            }

            @SubSection
            @ConfKey("manual")
            Manual manual();

            interface Manual {
                @ConfKey("title")
                String title();

                @ConfKey("input1")
                String input1();

                @ConfKey("input2")
                String input2();

                @ConfKey("input3")
                String input3();

                @ConfKey("input4")
                String input4();
            }

            @SubSection
            @ConfKey("expand")
            Expand expand();

            interface Expand {
                @ConfKey("title")
                String title();

                @ConfKey("dropdown")
                String dropdown();

                @ConfKey("input1")
                String input1();

                @ConfKey("input2")
                String input2();

                @ConfKey("toggle")
                String toggle();
            }

            @SubSection
            @ConfKey("create")
            ResCreate create();

            interface ResCreate {

                @ConfKey("title")
                String title();

                @ConfKey("cost")
                String cost();

                @ConfKey("input1")
                String input1();

                @ConfKey("input2")
                String input2();

            }
        }

        @SubSection
        @ConfKey("tool")
        Tool tool();

        interface Tool {
            @SubSection
            @ConfKey("main")
            Main main();

            interface Main {
                @ConfKey("title")
                String title();

                @ConfKey("content")
                String content();

                @SubSection
                @ConfKey("buttons")
                Buttons buttons();

                interface Buttons {

                    @ConfKey("check")
                    String check();

                    @ConfKey("query")
                    String query();

                }
            }

            @SubSection
            @ConfKey("query")
            Query query();

            interface Query {
                @ConfKey("title")
                String title();

                @ConfKey("dropdown")
                String dropdown();

                @ConfKey("input1")
                String input1();

                @ConfKey("input2")
                String input2();

                @ConfKey("choose")
                String choose();
            }

        }


        @SubSection
        @ConfKey("permission")
        Permission permission();

        interface Permission {

            @ConfKey("description")
            String description();

            @ConfKey("name")
            String name();

            @ConfKey("state")
            String state();

            @ConfKey("disabled")
            String disabled();

            @ConfKey("not-set")
            String notSet();

            @ConfKey("enable")
            String enable();
        }

        @SubSection
        @ConfKey("facing")
        Facing facing();

        interface Facing {

            @ConfKey("north")
            String north();

            @ConfKey("south")
            String south();

            @ConfKey("west")
            String west();

            @ConfKey("east")
            String east();

            @ConfKey("up")
            String up();

            @ConfKey("down")
            String down();

            @ConfKey("unknown")
            String unknown();

        }
    }
}
