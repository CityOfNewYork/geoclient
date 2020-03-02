/*
 * Copyright 2013-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nyc.doitt.gis.geoclient.doc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GroupDocumentationTest {
    private ItemDocumentation itemDocumentation;

    @BeforeEach
    public void setUp() {
        this.itemDocumentation = new ItemDocumentation();
        this.itemDocumentation.setId("abc123");
    }

    @Test
    public void testIsMember_groupMembersNull() {
        assertThrows(NullPointerException.class, () -> {
            new GroupDocumentation().isMember(itemDocumentation);
        });
    }

    @Test
    public void testIsMember() {
        GroupDocumentation groupDocumentation = new GroupDocumentation();
        List<GroupMember> members = new ArrayList<GroupMember>();
        groupDocumentation.setGroupMembers(members);
        assertFalse(groupDocumentation.isMember(itemDocumentation));
        GroupMember member = new GroupMember();
        member.setId(this.itemDocumentation.getId().substring(0, 3));
        members.add(member);
        assertTrue(groupDocumentation.isMember(itemDocumentation));
    }

}
