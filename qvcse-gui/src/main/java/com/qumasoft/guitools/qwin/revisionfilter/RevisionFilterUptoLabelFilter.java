/*   Copyright 2004-2014 Jim Voris
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.qumasoft.guitools.qwin.revisionfilter;

import com.qumasoft.qvcslib.LabelInfo;
import com.qumasoft.qvcslib.LogfileInfo;
import com.qumasoft.qvcslib.MergedInfoInterface;
import com.qumasoft.qvcslib.QVCSConstants;
import com.qumasoft.qvcslib.RevisionHeader;
import com.qumasoft.qvcslib.RevisionInformation;
import java.util.Objects;

/**
 * Up to label revision filter.
 * @author Jim Voris
 */
public class RevisionFilterUptoLabelFilter extends AbstractRevisionFilter {

    private final String filterLabel;

    /**
     * Create an up-to label revision filter.
     * @param label the label that defines the filter.
     * @param isANDFilter is this an 'AND' filter.
     */
    public RevisionFilterUptoLabelFilter(String label, boolean isANDFilter) {
        super(isANDFilter);
        filterLabel = label;
    }

    @Override
    public boolean passesFilter(FilteredRevisionInfo filteredRevisionInfo) {
        boolean retVal = false;
        MergedInfoInterface mergedInfo = filteredRevisionInfo.getMergedInfo();
        LogfileInfo logfileInfo = mergedInfo.getLogfileInfo();
        RevisionHeader filteredRevision = filteredRevisionInfo.getRevisionHeader();
        int filteredRevisionIndex = filteredRevisionInfo.getRevisionIndex();
        if (logfileInfo != null) {
            LabelInfo[] labels = logfileInfo.getLogFileHeaderInfo().getLabelInfo();
            if (labels != null) {
                for (LabelInfo label : labels) {
                    if (label.getLabelString().equals(filterLabel)) {
                        String labelRevisionString = label.getLabelRevisionString();
                        RevisionInformation revisionInformation = logfileInfo.getRevisionInformation();
                        int revisionIndex = revisionInformation.getRevisionIndex(labelRevisionString);
                        RevisionHeader revHeader = revisionInformation.getRevisionHeader(revisionIndex);
                        // What we need to do here is see if the revision we are evaluating is
                        // on the same branch.
                        if (revHeader.getDepth() == 0) {
                            // If the label is on the TRUNK.
                            if (filteredRevision.getDepth() == 0) {
                                if (filteredRevisionIndex >= revisionIndex) {
                                    // This TRUNK revision is the same or older than the TRUNK revision that has the label
                                    retVal = true;
                                }
                            }
                        } else {
                            // The label is on a branch.
                            // On a branch, older revisions occur BEFORE in the archive file...
                            if (filteredRevisionIndex <= revisionIndex) {
                                // If they are of the same depth... they might be on the same branch...
                                if (filteredRevision.getDepth() == revHeader.getDepth()) {
                                    // They are on the same branch if their revision strings match up to the
                                    // last minor number.
                                    String filteredRevisionString = filteredRevision.getRevisionString();
                                    int end = filteredRevisionString.lastIndexOf('.');
                                    String branchRevisionString = filteredRevisionString.substring(0, end);
                                    if (revHeader.getRevisionString().startsWith(branchRevisionString)) {
                                        retVal = true;
                                    }
                                } else if (filteredRevision.getDepth() < revHeader.getDepth()) {
                                    // If the revision we're looking at is closer to the TRUNK, then
                                    // it passes the filter if it is a direct ancestor of the
                                    // labeled (branch) revision,
                                    String revHeaderString = revHeader.getRevisionString();
                                    String filteredRevisionString = filteredRevision.getRevisionString();
                                    if (revHeaderString.startsWith(filteredRevisionString)) {
                                        retVal = true;
                                    }
                                }
                            } else {
                                // filteredRevisionIndex > revisionIndex
                                // Pick up the TRUNK revisions that are ancestors
                                // to this branch revision.
                                if (filteredRevision.getDepth() == 0) {
                                    retVal = true;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
        return retVal;
    }

    @Override
    public String getFilterType() {
        return QVCSConstants.UPTO_LABEL_FILTER;
    }

    @Override
    public String toString() {
        return QVCSConstants.UPTO_LABEL_FILTER;
    }

    @Override
    public String getFilterData() {
        return filterLabel;
    }

    @Override
    public boolean equals(Object o) {
        boolean retVal = false;
        if (o instanceof RevisionFilterUptoLabelFilter) {
            RevisionFilterUptoLabelFilter filter = (RevisionFilterUptoLabelFilter) o;
            if (filter.getFilterData().equals(getFilterData()) && (filter.getIsANDFilter() == this.getIsANDFilter())) {
                retVal = true;
            }
        }
        return retVal;
    }

    @Override
    public int hashCode() {
        // <editor-fold>
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.filterLabel);
        if (this.getIsANDFilter()) {
            hash += 97;
        }
        // </editor-fold>
        return hash;
    }
}
