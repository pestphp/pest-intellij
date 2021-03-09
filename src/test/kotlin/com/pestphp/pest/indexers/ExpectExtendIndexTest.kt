package com.pestphp.pest.indexers

import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.indexing.FileBasedIndex
import com.pestphp.pest.PestLightCodeFixture

class ExpectExtendIndexTest : PestLightCodeFixture() {
    override fun getTestDataPath(): String {
        return "src/test/resources/com/pestphp/pest/indexers/ExpectExtendIndexTest"
    }

    fun testExpectExtendIsIndexed() {
        myFixture.copyFileToProject("FileWithExpectExtension.php", "tests/FileWithExpectExtension.php")

        val fileBasedIndex = FileBasedIndex.getInstance()

        val indexKeys = fileBasedIndex.getAllKeys(PestTestIndex.key, project).filter {
            fileBasedIndex.getContainingFiles(PestTestIndex.key, it, GlobalSearchScope.allScope(project)).isNotEmpty()
        }

        assertContainsElements(indexKeys, "FileWithExpectExtension.php")
    }

    fun testNonExpectExtendIsNotIndexed() {
        myFixture.copyFileToProject("FileWithoutExpectExtension.php", "tests/FileWithoutExpectExtension.php")

        val fileBasedIndex = FileBasedIndex.getInstance()

        val indexKeys = fileBasedIndex.getAllKeys(PestTestIndex.key, project).filter {
            fileBasedIndex.getContainingFiles(PestTestIndex.key, it, GlobalSearchScope.allScope(project)).isNotEmpty()
        }

        assertDoesntContain(indexKeys, "FileWithoutExpectExtension.php")
    }
}
